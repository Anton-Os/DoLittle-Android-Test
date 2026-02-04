package com.zeroplus.dolittle.model;

import android.os.CountDownTimer;
import android.util.Log;

import com.zeroplus.dolittle.model.managers.ScriptManager;

class TimedEvent extends CountDownTimer {
    private ScriptManager.Callback tickCallback;
    private ScriptManager.Callback finishCallback;

    public TimedEvent(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.i("TEST", "Tick triggered");
        if (this.tickCallback != null) { this.tickCallback.execute(); }
    }

    @Override
    public void onFinish() {
        if (this.finishCallback != null) this.finishCallback.execute();
    }

    public void setOnTick(ScriptManager.Callback callback) { this.tickCallback = callback; }

    public void setOnFinish(ScriptManager.Callback callback) { this.finishCallback = callback; }
}
