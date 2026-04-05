package com.zeroplus.dolittle.model;

import java.util.ArrayList;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.future.FutureKt;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;
import androidx.compose.runtime.MutableState;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.ai.GeminiAPI;

public class Companion {
    public Companion(String nameArg, Pair<String, String> roleArg){
        name = nameArg;
        role = roleArg;
    }

    public Companion(String nameArg, Pair<String, String> roleArg, int imgResID){
        name = nameArg;
        role = roleArg;
        resourceID = imgResID;
    }

    public String getName(){ return name; }
    private String name;

    public String getRoleAdj(){ return role.first; } // gets the role adjective
    public String getRoleDesc(){ return role.second; } // gets the role description
    private Pair<String, String> role;

    public int getResID() { return resourceID; }

    private int resourceID = R.drawable.ic_launcher_foreground;

    public int getLitColorCode(){ return colorCodes[0]; }
    public int getAltColorCode(){ return colorCodes[1]; }

    public int getBtnColorCode(){ return colorCodes[2]; }

    public int getDrkColorCode(){ return colorCodes[3]; }

    // public void setDialouge(){}

    protected int[] colorCodes = { 0xFFfffef0, 0xFFffd86b, 0xFFfff200, 0xFF111111 };

    @RequiresApi(api = Build.VERSION_CODES.O)
    // public Future<Script.ScriptContent> query(String inputStr) {
    public void getAdvice(String inputStr, MutableState<String> dialouge) throws InterruptedException {
        String prompt = "\nYour name is " + getName() + " and your role is to" + getRoleAdj() + ": " + getRoleDesc()
        + "Respond to the user in one sentence based on their latest thought: " + inputStr;

        FutureKt.future(GlobalScope.INSTANCE, GlobalScope.INSTANCE.getCoroutineContext(), CoroutineStart.DEFAULT,
                (scope, continuation) ->
                        GeminiAPI.INSTANCE.promptToTxt(prompt, continuation)
        ).thenAccept(result -> {
            dialouge.setValue(result.toString());
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
    public ArrayList<Script.ScriptContent> getDialouges(){ return dialouges; }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();

    private boolean isUnlocked = true; // generic companions unlocked by default and specialized one via micro-transactions
}
