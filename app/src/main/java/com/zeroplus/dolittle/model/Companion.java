package com.zeroplus.dolittle.model;

import static android.util.Log.println;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.future.FutureKt;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
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

    public String getVetting(){ return vettingStr; }

    protected String vettingStr; // for vetting response

    public int getResID() { return resourceID; }

    private int resourceID = R.drawable.ic_launcher_foreground;

    public int getLitColorCode(){ return colorCodes[0]; }
    public int getAltColorCode(){ return colorCodes[1]; }

    public int getBtnColorCode(){ return colorCodes[2]; }

    public int getDrkColorCode(){ return colorCodes[3]; }

    // public void setDialouge(){}

    protected int[] colorCodes = { 0xFFfffef0, 0xFFffd86b, 0xFFfff200, 0xFF2b2b29 };

    public Map<Script.Content, String> getDialouges(){ return Collections.unmodifiableMap(dialougesMap); }
    // protected ArrayList<Script.Content> dialouges = new ArrayList<>();

    protected HashMap<Script.Content, String> dialougesMap = new HashMap<Script.Content, String>();

    private boolean isUnlocked = true; // generic companions unlocked by default and specialized one via micro-transactions

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    // public Future<Script.ScriptContent> query(String inputStr) {
    public void getAdvice(Script.Content content, MutableState<String> dialouge) throws InterruptedException {
        if(dialougesMap.containsKey(content)){ // check to avoid duplicate content
            println(Log.WARN, "STATUS", "Duplicate script detected");
            return;
        }

        String prompt = "\nYour name is " + getName() + " and your role is to" + getRoleAdj() + ": " + getRoleDesc()
        + "Respond to the user in one sentence based on their latest thought: " + content.getContent();

        FutureKt.future(GlobalScope.INSTANCE, GlobalScope.INSTANCE.getCoroutineContext(), CoroutineStart.DEFAULT,
                (scope, continuation) ->
                        GeminiAPI.INSTANCE.promptsToTxt(new ArrayList<>(List.of(prompt, vettingStr)), continuation)
        ).thenAccept(result -> {
            if(result != null) {
                dialouge.setValue(result.toString());
                dialougesMap.put(content, result.toString());
                println(Log.INFO, "STATUS", "Dialouges map is " + dialougesMap.toString());
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    /* public void getOptions(Script.Content content, MutableState<List<String>> options){
        // TODO: Implement here
    } */
}
