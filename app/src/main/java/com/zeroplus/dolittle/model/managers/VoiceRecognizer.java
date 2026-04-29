package com.zeroplus.dolittle.model.managers;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.util.Log;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1p1beta1.StreamingRecognitionResult;
import com.google.cloud.speech.v1p1beta1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1p1beta1.StreamingRecognizeResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/* import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine; */

// Refer to Google Cloud documentation in https://docs.cloud.google.com/speech-to-text/docs/v1/transcribe-streaming-audio#speech-streaming-recognize-java

public class VoiceRecognizer extends VoiceManager{
    public VoiceRecognizer(Context context) throws IOException {
        super(context);

        speechClient = SpeechClient.create();

        responseObserver = new ResponseObserver<StreamingRecognizeResponse>(){
            @Override
            public void onStart(StreamController controller) {
                Log.d("VoiceRecognizer", "onStart() triggered");
            }

            @Override
            public void onResponse(StreamingRecognizeResponse response) {
                Log.d("VoiceRecognizer", "onResponse() triggered");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("VoiceRecognizer", "onError() triggered");
            }

            @Override
            public void onComplete() {
                Log.d("VoiceRecognizer", "onComplete() triggered");
            }
        };
    }

    /* public static void listen(){
        // if (!AudioSystem.isLineSupported(targetInfo)) {
        //    System.out.println("Microphone not supported");
        //    System.exit(0);
        //}

        recognitionConfig = RecognitionConfig.newBuilder()
                // .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16) // TODO: Uncomment this!
                .setLanguageCode("en-US")
                .setSampleRateHertz(sampleRateHz)
                .build();

        StreamingRecognitionConfig streamRecConfig = StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

        StreamingRecognizeRequest streamRequest = StreamingRecognizeRequest.newBuilder().setStreamingConfig(streamRecConfig).build();

        ClientStream<StreamingRecognizeRequest> clientStream = speechClient.streamingRecognizeCallable().splitCall(responseObserver);
        clientStream.send(streamRequest);

        // AudioFormat audioFormat = new AudioFormat.Builder().setSampleRate(sampleRateHz).build(); // TODO: Check if works
    } */

    private static SpeechClient speechClient;
    private static RecognitionConfig recognitionConfig;

    private static ResponseObserver<StreamingRecognizeResponse> responseObserver;

    private static int sampleRateHz = 16000;
    private static int sampleBitSize = 16;
}
