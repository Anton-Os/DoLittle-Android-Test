package com.zeroplus.dolittle.model;

import java.util.ArrayList;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.GlobalScope;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.R;
import com.zeroplus.dolittle.model.managers.GeminiAPI;

public class Companion extends GeminiAPI {
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

    protected int[] colorCodes = { 0xFFfffef0, 0xFFffd86b, 0xFFfff200, 0xFF111111 };

    @RequiresApi(api = Build.VERSION_CODES.O)
    // public Future<Script.ScriptContent> query(String inputStr) {
    public Script.ScriptContent getAdvice(String inputStr) throws InterruptedException {
        String content = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String prompt = "\nYour role is: " + getRoleDesc() + "In one sentence, provide a response to the users latest thought:" + inputStr;
            content = BuildersKt.runBlocking(GlobalScope.INSTANCE.getCoroutineContext(),
                (scope, continuation) -> promptToTxt(prompt, continuation)
            );
            /* content = BuildersKt.launch(GlobalScope.INSTANCE, null, null,
                    (scope, continuation) -> promptToTxt(prompt, continuation);
            ); */
        }
        dialouges.add(new Script.ScriptContent(content));
        return dialouges.get(dialouges.size() - 1);
    }
    public ArrayList<Script.ScriptContent> getDialouges(){ return dialouges; }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();

    private boolean isUnlocked = true; // generic companions unlocked by default and specialized one via micro-transactions
}
