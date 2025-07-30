package com.zeroplus.dolittle.model;

import java.util.ArrayList;

public class Companion {
    public Companion(String nameArg, String roleArg){
        name = nameArg;
        role = roleArg;
    }


    public String getName(){ return name; }
    private String name;

    public String getRole(){ return role; }
    private String role;

    public ArrayList<Script.ScriptContent> getDialouges(){ return dialouges; }
    protected ArrayList<Script.ScriptContent> dialouges = new ArrayList<>();
}
