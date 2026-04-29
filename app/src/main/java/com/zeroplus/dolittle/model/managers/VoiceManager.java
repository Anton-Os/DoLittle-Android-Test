package com.zeroplus.dolittle.model.managers;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.util.Log; // Recommended for logging in Android
import android.util.Pair;

import com.zeroplus.dolittle.MainActivity; // Assuming MainActivity.scriptManager is static
import com.zeroplus.dolittle.model.Users;
import com.zeroplus.dolittle.model.Script;

public class VoiceManager {

    public VoiceManager(Context context) {
        // Built-In Speech Recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        SpeechListener speechListener = new SpeechListener(); // Instantiate the inner class
        speechRecognizer.setRecognitionListener(speechListener);
    }
    public static void listen(Intent speechIntent, ListenerCallback callback){
        savedIntent = speechIntent;
        speechRecognizer.startListening(speechIntent);
        SpeechListener.listenerCallback = callback;
        changeListenState(true);
    }

    public void destroy() {
        if (speechRecognizer != null) speechRecognizer.destroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    protected static class SpeechListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) { Log.d("VoiceManager", "onReadyForSpeech() triggered"); }

        @Override
        public void onBeginningOfSpeech() { Log.d("VoiceManager", "onBeginningOfSpeech() triggered"); }

        @Override
        public void onRmsChanged(float rmsdB) { Log.d("VoiceManager", "onRmsChanged() triggered"); }

        @Override
        public void onBufferReceived(byte[] buffer) { Log.d("VoiceManager", "onBufferReceived() triggered"); }

        @Override
        public void onEndOfSpeech() { Log.d("VoiceManager", "onEndOfSpeech() triggered"); }

        @Override
        public void onError(int error) {
            Log.e("VoiceManager", "onError() triggered with code: " + error);
            changeListenState(false);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d("VoiceManager", "onPartialResults() triggered");
            if(partialResults != null) analyzeSpeech(partialResults);
            changeListenState(false);
        }

        @Override
        public void onResults(Bundle results) {
            Log.d("VoiceManager", "onResults() triggered");
            if(results != null) analyzeSpeech(results);
            changeListenState(false);
        }

        @Override
        public void onEvent(int eventType, Bundle params) { Log.d("VoiceManager", "onEvent() triggered, eventType: " + eventType); }

        @SuppressLint("NewApi")
        public void analyzeSpeech(Bundle bundle) {
            ArrayList<String> words = bundle.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = bundle.getFloatArray(android.speech.SpeechRecognizer.CONFIDENCE_SCORES);

            if (words != null)
                for (String word : words) {
                    MainActivity.scriptManager.scripts.add(Pair.create(new Script(word), new Users.Self("User"))); // TODO: Determine speaker
                }
        }

        protected static ListenerCallback listenerCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    protected static void changeListenState(boolean isListeningArg){
        isListening = isListeningArg;
        SpeechListener.listenerCallback.changeListenState(isListening);

        if(!isListening && isPersistent) listen(savedIntent, SpeechListener.listenerCallback); // launch listen again persistently
    }

    protected static android.speech.SpeechRecognizer speechRecognizer;

    protected static Intent savedIntent;

    public static boolean getIsListening(){ return isListening; }

    protected static boolean isListening = false;

    public static boolean isPersistent = true;

    public interface ListenerCallback {
        void changeListenState(Boolean isListen);
    }

}