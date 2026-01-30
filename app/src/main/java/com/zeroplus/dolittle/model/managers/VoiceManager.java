package com.zeroplus.dolittle.model.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.util.Log; // Recommended for logging in Android

import com.google.cloud.speech.v1.SpeechClient;
/* import com.google.cloud.speech.v2.AutoDetectDecodingConfig;
import com.google.cloud.speech.v2.CreateRecognizerRequest;
import com.google.cloud.speech.v2.OperationMetadata;
import com.google.cloud.speech.v2.RecognitionConfig;
import com.google.cloud.speech.v2.RecognizeRequest;
import com.google.cloud.speech.v2.RecognizeResponse;
import com.google.cloud.speech.v2.Recognizer;
import com.google.cloud.speech.v2.SpeechClient;
import com.google.cloud.speech.v2.SpeechRecognitionAlternative; */

import com.zeroplus.dolittle.MainActivity; // Assuming MainActivity.scriptManager is static
import com.zeroplus.dolittle.model.Speakers;
import com.zeroplus.dolittle.model.Script;

public class VoiceManager {

    public VoiceManager(Context context) {
        mediaRecorder = new MediaRecorder(); // Media Recorder
        // Built-In Speech Recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        SpeechListener speechListener = new SpeechListener(); // Instantiate the inner class
        speechRecognizer.setRecognitionListener(speechListener);
    }
    public static void listen(Intent speechIntent){
        isListening = true;
        speechRecognizer.startListening(speechIntent);
    }

    public static void setupSpeech(String outputFile) {
        // Media Recorder
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(outputFile);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Log.e("VoiceManager", "Recording failed: $e");
            throw new RuntimeException(e);
        }

        // Google Speech-to-Text
        // try { speechClient = SpeechClient.create(); }
        // catch (IOException e) { throw new RuntimeException(e); }
    }

    public static boolean getIsListening(){ return isListening; }

    public void destroy() {
        if (speechRecognizer != null) speechRecognizer.destroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private static class SpeechListener implements RecognitionListener {
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
        public void onPartialResults(Bundle partialResults) {
            Log.d("VoiceManager", "onPartialResults() triggered");
            if(partialResults != null) analyzeSpeech(partialResults);
            isListening = false; // finished listening // TODO: Trigger listening loop?
        }

        @Override
        public void onResults(Bundle results) {
            Log.d("VoiceManager", "onResults() triggered");
            if(results != null) analyzeSpeech(results);
            isListening = false; // finished listening // TODO: Trigger listening loop?
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d("VoiceManager", "onEvent() triggered, eventType: " + eventType);
        }

        public void analyzeSpeech(Bundle bundle) {
            ArrayList<String> words = bundle.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = bundle.getFloatArray(android.speech.SpeechRecognizer.CONFIDENCE_SCORES);

            if (words != null) {
                for (String word : words) {
                    Map<Script, Speakers.Speaker> tempMap = new HashMap<>();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        tempMap.put(new Script(word), new Speakers.User("Me"));
                    MainActivity.scriptManager.scripts.putAll(tempMap);
                    Log.d("VoiceManager", "Results triggered with " + word);
                }
            }
        }
    }

    private static android.speech.SpeechRecognizer speechRecognizer;

    private static MediaRecorder mediaRecorder;

    private static SpeechClient speechClient; // From Google Speech-to-Text

    private static boolean isListening = false;

    // TODO: Insert callback here for listening
}