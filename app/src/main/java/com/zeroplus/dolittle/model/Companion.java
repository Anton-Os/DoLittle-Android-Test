package com.zeroplus.dolittle.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zeroplus.dolittle.model.managers.GeminiAPI;

import java.util.ArrayList;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.GlobalScope;

public class Companion extends GeminiAPI {
    public Companion(String nameArg, String roleArg){
        name = nameArg;
        role = roleArg;
    }

    public String getName(){ return name; }
    private String name;
    public String getRole(){ return role; }
    private String role;

    // private Color color = LightGray
    @RequiresApi(api = Build.VERSION_CODES.O)
    // public Future<Script.ScriptContent> query(String inputStr) {
    public Script.ScriptContent getAdvice(String inputStr) throws InterruptedException {
        String content = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String prompt = "\nYour role is: " + getRole() + "In one sentence, provide a response to the users latest thought:" + inputStr;
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
    // public Script.ScriptContent getRandAdvice(){ return dialouges.get((int)Math.floor(Math.random() * dialouges.size())); }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();
}
