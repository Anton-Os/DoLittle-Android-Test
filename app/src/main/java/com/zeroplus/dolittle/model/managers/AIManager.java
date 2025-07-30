package com.zeroplus.dolittle.model.managers;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.Content;
import com.zeroplus.dolittle.BuildConfig;

public class AIManager {
    public interface TextGenerationCallback {
        void onSuccess(String generatedText);
        void onFailure(Throwable error); // Include error handling for robust code
    }
    public String promptToTxt(String prompt, TextGenerationCallback callback){
        Content content = new Content.Builder().addText(prompt).build();
        // model.generateContent(prompt, );

        return "";
    }
    private GenerativeModel model = new GenerativeModel(
        "gemini-2.0-flash-001",
        BuildConfig.apiKey
    );
}
