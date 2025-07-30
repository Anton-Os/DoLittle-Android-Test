package com.zeroplus.dolittle.model.managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log; // Recommended for logging in Android

import com.zeroplus.dolittle.MainActivity; // Assuming MainActivity.scriptManager is static
import com.zeroplus.dolittle.model.Avatar;
import com.zeroplus.dolittle.model.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoiceManager {

    private final SpeechRecognizer speechRecognizer;
    private final SpeechRecognitionListenerImpl speechRecognitionListener;

    public VoiceManager(Context context) {
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        this.speechRecognitionListener = new SpeechRecognitionListenerImpl(); // Instantiate the inner class
    }

    // Inner class to implement RecognitionListener, similar to Kotlin's 'object'
    private class SpeechRecognitionListenerImpl implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d("VoiceManager", "onReadyForSpeech() triggered");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("VoiceManager", "onBeginningOfSpeech() triggered");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d("VoiceManager", "onRmsChanged() triggered");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d("VoiceManager", "onBufferReceived() triggered");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d("VoiceManager", "onEndOfSpeech() triggered");
        }

        @Override
        public void onError(int error) {
            Log.e("VoiceManager", "onError() triggered with code: " + error);
        }

        @Override
        public void onResults(Bundle results) {
            Log.d("VoiceManager", "onResults() triggered");
            if (results != null) analyzeSpeech(results);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d("VoiceManager", "onPartialResults() triggered");
            if (partialResults != null) analyzeSpeech(partialResults);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d("VoiceManager", "onEvent() triggered, eventType: " + eventType);
        }

        public void analyzeSpeech(Bundle bundle) {
            ArrayList<String> words = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = bundle.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            if (words != null) {
                for (String word : words) {
                    Map<Script, Avatar> tempMap = new HashMap<>();
                    tempMap.put(new Script(word), new Avatar("Me"));

                    if (MainActivity.scriptManager != null) {
                        MainActivity.scriptManager.scripts.putAll(tempMap);
                    } else {
                        Log.e("VoiceManager", "scriptManager is null! Cannot add script.");
                    }
                    Log.d("VoiceManager", "Results triggered with " + word);
                }
            }
        }
    }

    public void setupSpeech() {
        speechRecognizer.setRecognitionListener(speechRecognitionListener);

        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // Uncomment if you need to set locale
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");

        speechRecognizer.startListening(speechRecognizerIntent);
    }

    // Don't forget to destroy the SpeechRecognizer when no longer needed
    public void destroy() {
        if (speechRecognizer != null) speechRecognizer.destroy();
    }
}