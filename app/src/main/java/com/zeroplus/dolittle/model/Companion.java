package com.zeroplus.dolittle.model;

import android.graphics.Color;

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
    public abstract String feedback(String inputStr);
    public ArrayList<Script.ScriptContent> getDialouges(){ return dialouges; }
    public Script.ScriptContent getRandAdvice(){ return dialouges.get((int)Math.floor(Math.random() * dialouges.size())); }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();
}
