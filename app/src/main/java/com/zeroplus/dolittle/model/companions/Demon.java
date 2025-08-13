package com.zeroplus.dolittle.model.companions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Demon extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Demon() {
        super("Demon", "Criticizes you and understands your worst vices");

        // dialouges.add(new Script.ScriptContent("Love to drink"));
        // dialouges.add(new Script.ScriptContent("Love to smoke"));
        // dialouges.add(new Script.ScriptContent("You stupid bloke"));
    }
}
