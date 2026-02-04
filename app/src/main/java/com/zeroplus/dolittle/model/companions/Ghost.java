package com.zeroplus.dolittle.model.companions;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.Companion;
import com.zeroplus.dolittle.model.Script;

public class Ghost extends Companion {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ghost() {
        super("Ghost", new Pair<>("Remind", "Reminds you of your past and habits"), R.drawable.ghost);
        colorCodes = new int[]{ 0xFFdcdfe3, 0xFFafafaf, 0xFFeeeeee };
    }
}
