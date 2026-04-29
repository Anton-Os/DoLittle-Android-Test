package com.zeroplus.dolittle.model.companions;

import android.graphics.Color;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Angel extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Angel() {
        super("Your Moral Compass", new Pair<>("Motivate", "Offers encouragement and makes you feel better about yourself"), R.drawable.angel);

        colorCodes = new int[]{ 0xFFd3eff0, 0xFF6783f5, 0xFF00c8ff, 0xFF222244 };
        vettingStr = "Be sure to embellish your response with emojis, a memorable example, or a quote to appear more holy";
    }
}
