package com.zeroplus.dolittle.model.companions;

import android.graphics.Color;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Brain extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Brain() {
        super("Jimminy the Talking Head", new Pair<>("Question", "Finds out more about you"), R.drawable.brain);
        colorCodes = new int[]{ 0xFFfffef0, 0xFFffd86b, 0xFFfff200, 0xFF2b2b29 };
    }
}
