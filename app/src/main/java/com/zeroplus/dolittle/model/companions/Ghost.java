package com.zeroplus.dolittle.model.companions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.managers.AIManager;

public class Ghost extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ghost() {
        super("Ghost", "Always there to remind you of somethign");

        dialouges.add(new Script.ScriptContent("Remember the past"));
        dialouges.add(new Script.ScriptContent("Know this wont last"));
        dialouges.add(new Script.ScriptContent("Cause life goes fast"));
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
