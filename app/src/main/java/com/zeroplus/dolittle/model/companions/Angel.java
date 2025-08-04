package com.zeroplus.dolittle.model.companions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.managers.AIManager;

public class Angel extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Angel() {
        super("Angel", "Offers encouragement and brings positive vibes to your life");

        dialouges.add(new Script.ScriptContent("Stay Healthy"));
        dialouges.add(new Script.ScriptContent("Stay Cool"));
        dialouges.add(new Script.ScriptContent("Stay in School"));
    }

    public String feedback(String inputStr){
        final String[] genTxt = { "" };
        AIManager.promptToTxt(inputStr, new AIManager.TextGenerationCallback() {
            @Override
            public void onSuccess(String generatedText) { genTxt[0] = generatedText; }

            @Override
            public void onFailure(Throwable error) { System.out.println("Error is $error"); }
        });
        return genTxt[0];
    }
}
