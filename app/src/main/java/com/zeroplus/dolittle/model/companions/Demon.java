package com.zeroplus.dolittle.model.companions;

import android.graphics.Color;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Demon extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Demon() {
        super("Devil's Advocate", new Pair<>("Heckle", "Criticizes you and plays devils advocate"), R.drawable.devil);
        colorCodes = new int[]{ 0xFFf0d5d3, 0xFFf06277, 0xFFff2600, 0xFF442222 };
    }
}
