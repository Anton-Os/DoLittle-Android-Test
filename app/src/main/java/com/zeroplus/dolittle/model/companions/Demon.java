package com.zeroplus.dolittle.model.companions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.managers.AIManager;

public class Demon extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Demon() {
        super("Demon", "Criticizes you and understands your worst vices");

        dialouges.add(new Script.ScriptContent("Love to drink"));
        dialouges.add(new Script.ScriptContent("Love to smoke"));
        dialouges.add(new Script.ScriptContent("You stupid bloke"));
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
