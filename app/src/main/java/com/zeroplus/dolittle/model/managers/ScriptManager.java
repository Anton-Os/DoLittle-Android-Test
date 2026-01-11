package com.zeroplus.dolittle.model.managers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zeroplus.dolittle.model.Avatar;
import com.zeroplus.dolittle.model.Script;

public class ScriptManager {

    public ScriptManager() {
        loadScripts(""); // test

        // Set up the timed event callbacks
        timedEvent.setOnTick(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void execute() { /* scripts.put(new Script("What should I do today?"), new Avatar("Me")); */ }
        });
        timedEvent.setOnFinish(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void execute() { /* scripts.put(new Script("How should I end my day?"), new Avatar("Me")); */ }
        });
        timedEvent.start();
    }

    @SuppressLint("NewApi")
    public void loadScripts(String filePath) {
        if(filePath.isEmpty()) { // test
            scripts.put(new Script("Hello how are you?"), new Avatar("Avatar 1"));
            // scripts.put(new Script("Im Good, thanks"), new Avatar("Avatar 2"));
            // scripts.put(new Script("Lovely weather today"), new Avatar("Avatar 3"));
            // scripts.put(new Script("I suppose so"), new Avatar("Avatar 4"));
        } else {
            StringBuilder strBuilder = new StringBuilder();

            File scriptFile = new File(filePath);
            try(FileReader reader = new FileReader(scriptFile)){
                int readChar = reader.read();
                while(readChar != -1) {
                    strBuilder.append(readChar);
                    readChar = reader.read();
                }
            } catch (IOException e) {
                Log.e("ScriptManager", "Error loading scripts: " + e.getMessage());
            }

            // TODO: Parse built String and append data
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public void saveScripts(String filePath) {
        File scriptFile = new File(filePath);
        try (FileWriter writer = new FileWriter(scriptFile, true)) { // true for append mode
            for (Map.Entry<Script, Avatar> entry : scripts.entrySet()) {
                writer.write("Avatar: " + entry.getValue().getName() + "\n");
                for (Script.ScriptContent c : entry.getKey().getScriptContents()) {
                    writer.write(c.getTimestamp() + ":" + c.getContent() + "\n");
                }
            }
        } catch (IOException e) {
            Log.e("ScriptManager", "Error saving scripts: " + e.getMessage());
        }
    }

    public void addScript(Script script, Avatar avatar){ scripts.put(script, avatar); }

    // Callback interface for TimedEvent
    public interface Callback { void execute(); }

    public Map<Script, Avatar> scripts = new LinkedHashMap<>();
    public static class TimedEvent extends CountDownTimer {
        private Callback tickCallback;
        private Callback finishCallback;

        public TimedEvent(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("TEST", "Tick triggered");
            if (this.tickCallback != null) { this.tickCallback.execute(); }
        }

        @Override
        public void onFinish() {
            if (this.finishCallback != null) this.finishCallback.execute();
        }

        public void setOnTick(Callback callback) { this.tickCallback = callback; }

        public void setOnFinish(Callback callback) { this.finishCallback = callback; }
    }

    public static TimedEvent timedEvent = new TimedEvent(10000, 1000);
}