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
        super("Demon", "Criticizes you and understands your worst vices", R.drawable.devil);
        colorCodes = new Pair<>(0xFFf0d5d3, 0xFFf2cbce);
    }
}
