package com.zeroplus.dolittle.model;

import android.os.Build;

import com.zeroplus.dolittle.model.managers.AIManager;

import java.util.ArrayList;

public abstract class Companion {
    public Companion(String nameArg, String roleArg){
        name = nameArg;
        role = roleArg;
    }

    public String getName(){ return name; }
    private String name;
    public String getRole(){ return role; }
    private String role;

    // private Color color = LightGray
    public Script.ScriptContent query(String inputStr) {
        Script.ScriptContent[] content = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            content = new Script.ScriptContent[]{ new Script.ScriptContent("") };
            Script.ScriptContent[] finalContent = content;

            String prompt = "You must analyze the users thoughts: " + inputStr + "\nYour role is: " + getRole();
            AIManager.promptToTxt(prompt, new AIManager.TextGenerationCallback() {
                @Override
                public void onSuccess(String generatedText) {
                    finalContent[0] = new Script.ScriptContent(generatedText);
                    System.out.println("Final content is " + finalContent[0].getContent());
                    dialouges.add(finalContent[0]);
                }

                @Override
                public void onFailure(Throwable error) {
                    System.out.println("Error is $error");
                }
            });
        }
        assert content != null;
        return content[0];
    }
    public ArrayList<Script.ScriptContent> getDialouges(){ return dialouges; }
    // public Script.ScriptContent getRandAdvice(){ return dialouges.get((int)Math.floor(Math.random() * dialouges.size())); }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();
}
