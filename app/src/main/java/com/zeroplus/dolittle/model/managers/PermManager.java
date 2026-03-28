package com.zeroplus.dolittle.model.managers;

import static com.zeroplus.dolittle.MainActivityKt.getActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log; // Used for printing log messages

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom; // For Random.nextInt() equivalent

public class PermManager {

    // Mimicking Kotlin's lateinit var missingRequiredPermissions
    public static String[] missingRequiredPermissions;

    // --- Perm inner class ---
    public static class Perm {
        // Mimicking Kotlin's 'by lazy' for SharedPreferences
        private static SharedPreferences storageInstance;

        public static SharedPreferences getStorage() {
            if (storageInstance == null) {
                if (Objects.requireNonNull(getActivity()) == null) { // Assuming Objects.requireNonNull(getActivity()) is how you access your Application or current Activity instance
                    throw new IllegalStateException("null activity value");
                }
                storageInstance = Objects.requireNonNull(getActivity()).getSharedPreferences(Objects.requireNonNull(getActivity()).getPackageName() + ".permission", Context.MODE_PRIVATE);
            }
            return storageInstance;
        }

        public static final String BLUETOOTH = Manifest.permission.BLUETOOTH;
        public static final String BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN;
        @RequiresApi(Build.VERSION_CODES.S)
        public static final String BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT;
        @RequiresApi(Build.VERSION_CODES.S)
        public static final String BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN;

        public static final String[] BLUETOOTH_PERMS;

        static {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                BLUETOOTH_PERMS = new String[]{BLUETOOTH, BLUETOOTH_ADMIN};
            } else {
                BLUETOOTH_PERMS = new String[]{BLUETOOTH_SCAN, BLUETOOTH_CONNECT};
            }
        }

        public static final String BACKGROUND_PROCESSING = "dolittle.permission.ACCESS_BACKGROUND_PROCESSING";
        @RequiresApi(Build.VERSION_CODES.Q)
        public static final String BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;

        public static final String[] BACKGROUND_PERMS;

        static {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                BACKGROUND_PERMS = new String[]{BACKGROUND_PROCESSING};
            } else {
                BACKGROUND_PERMS = new String[]{BACKGROUND_PROCESSING, BACKGROUND_LOCATION};
            }
        }

        public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String[] LOCATION_PERMS = new String[]{FINE_LOCATION, COARSE_LOCATION};

        public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

        public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        public static final String[] USE_ALARMS = new String[] {
                Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.USE_EXACT_ALARM
        };

        public static final String POST_NOTIFICATIONS;

        static {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS;
            } else {
                POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";
            }
        }

        public static final String[] ALL_PERMISSIONS;

        static {
            Set<String> allPermsSet = new HashSet<>();
            allPermsSet.addAll(Arrays.asList(BLUETOOTH_PERMS));
            allPermsSet.addAll(Arrays.asList(BACKGROUND_PERMS));
            allPermsSet.addAll(Arrays.asList(LOCATION_PERMS));
            allPermsSet.add(RECORD_AUDIO);
            allPermsSet.add(READ_STORAGE);
            // Write storage is usually implied by Read, so often not explicitly added unless needed
            // allPermsSet.add(WRITE_STORAGE);
            allPermsSet.add(POST_NOTIFICATIONS);
            ALL_PERMISSIONS = allPermsSet.toArray(new String[0]);
        }

        public static boolean hasRequested(String perm) {
            boolean hasRequested = getStorage().getBoolean(perm, false);
            Log.d("PermManager", perm + " has " + (hasRequested ? "" : "not ") + "been requested before");
            return hasRequested;
        }

        public static void requesting(String[] perms) {
            SharedPreferences.Editor editor = getStorage().edit();
            for (String perm : perms) {
                if (!perm.equals(BACKGROUND_PROCESSING)) { // Assuming backgroundProcessing should not be marked as requested directly
                    editor.putBoolean(perm, true);
                }
            }
            editor.apply();
        }
    }

    // --- RequestResult Enum ---
    public enum RequestResult {
        GRANTED, DENIED, FAILED
    }

    // --- PermRequest inner class ---
    private static class PermRequest {
        public interface Callback {
            void onResult(RequestResult result);
        }

        public final Callback callback;
        public final long time;

        public PermRequest(Callback callback) {
            this.callback = callback;
            this.time = System.currentTimeMillis();
        }
    }

    private static Map<Integer, PermRequest> permRequestCallbacks = new HashMap<>();

    // --- Public methods ---

    public static void checkAll() {
        // Initialize missingRequiredPermissions to an empty array
        missingRequiredPermissions = new String[0];

        // if (Perm.acceptedPrivacyPolicy < API.privacyPolicyVersion)
        // missingRequiredPermissions = append(missingRequiredPermissions, Perm.privacyPolicy); // Assuming you'll have an append method or similar logic

        if (!granted(Perm.BLUETOOTH_PERMS)) {
            missingRequiredPermissions = append(missingRequiredPermissions, Perm.BLUETOOTH_PERMS);
        }
        if (!granted(Perm.LOCATION_PERMS)) {
            missingRequiredPermissions = append(missingRequiredPermissions, Perm.LOCATION_PERMS);
        }
        if (!granted(Perm.BACKGROUND_PERMS)) {
            missingRequiredPermissions = append(missingRequiredPermissions, Perm.BACKGROUND_PERMS);
        }
    }

    // Helper method to append arrays (mimics Kotlin's '+=' on arrays)
    private static String[] append(String[] originalArray, String[] elementsToAdd) {
        String[] newArray = Arrays.copyOf(originalArray, originalArray.length + elementsToAdd.length);
        System.arraycopy(elementsToAdd, 0, newArray, originalArray.length, elementsToAdd.length);
        return newArray;
    }


    public static boolean granted(String perm) {
        if (perm.equals(Perm.POST_NOTIFICATIONS) && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        } else if (Arrays.asList(Perm.ALL_PERMISSIONS).contains(perm)) {
            if (Objects.requireNonNull(getActivity()) == null) {
                throw new IllegalStateException("null activity value");
            }
            return ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), perm) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static boolean granted(String[] perms) {
        for (String perm : perms) {
            if (!granted(perm)) {
                Log.d("PermManager", perm + " has not been granted");
                return false;
            }
        }
        Log.d("PermManager", Arrays.toString(perms) + " has been granted");
        return true;
    }

    public static void request(String perm, PermRequest.Callback callback) {
        request(new String[]{perm}, callback);
    }

    public static void request(String[] perms, PermRequest.Callback callback) {
        if (granted(perms)) {
            callback.onResult(RequestResult.GRANTED);
            return;
        }

        // Generate a random request code, similar to Kotlin's Random.nextInt()
        int code = ThreadLocalRandom.current().nextInt(1, 1000); // Or use a static counter if you prefer
        while (permRequestCallbacks.containsKey(code)) { // Ensure unique code
            code = ThreadLocalRandom.current().nextInt(1, 1000);
        }

        permRequestCallbacks.put(code, new PermRequest(callback));
        Log.d("PermManager", "requesting permissions " + Arrays.toString(perms) + " with code " + code);
        Perm.requesting(perms);
        if (Objects.requireNonNull(getActivity()) == null) {
            throw new IllegalStateException("null activity value");
        }
        Objects.requireNonNull(getActivity()).requestPermissions(perms, code);
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("PermManager", "onRequestPermissionResult(requestCode: " + requestCode + ", permissions: " + Arrays.toString(permissions) + ", grantResults: " + Arrays.toString(grantResults) + ")");

        PermRequest request = permRequestCallbacks.get(requestCode);
        if (request == null) {
            return;
        }

        long waitTime = System.currentTimeMillis() - request.time;
        Log.d("PermManager", "request wait time = " + waitTime + "ms");

        RequestResult result;
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                allGranted = false;
                break;
            }
        }

        if (allGranted) {
            result = RequestResult.GRANTED;
        } else if (waitTime < 90) { // Arbitrary threshold from Kotlin code
            result = RequestResult.FAILED;
        } else {
            result = RequestResult.DENIED;
        }

        request.callback.onResult(result);
        permRequestCallbacks.remove(requestCode);
    }
}