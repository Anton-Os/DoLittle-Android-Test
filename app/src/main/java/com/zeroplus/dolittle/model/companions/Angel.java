package com.zeroplus.dolittle.model.companions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Angel extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Angel() {
        super("Angel", "Offering encouragement and bringing positive vibes to your life");

        // dialouges.add(new Script.ScriptContent("Stay Healthy"));
        // dialouges.add(new Script.ScriptContent("Stay Cool"));
        // dialouges.add(new Script.ScriptContent("Stay in School"));
    }
}
