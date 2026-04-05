package com.zeroplus.dolittle.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

public class Script {
    // public Script(ArrayList<ScriptContent> scriptContentsArg){
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Script(String textArg){
        // scriptContents = scriptContentsArg;
        scriptContents.add(new ScriptContent(textArg));
    }
    public static class ScriptContent {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public ScriptContent(String contentArg){ content = contentArg; }

        public String getContent(){ return content; }
        private String content;
    }

    public ArrayList<ScriptContent> getScriptContents(){ return scriptContents; }

    public String getPrintedScript(){
        StringBuilder printedScript = new StringBuilder();
        for(ScriptContent s : scriptContents) printedScript.append(s.getContent());
        return printedScript.toString();
    }
    private ArrayList<ScriptContent> scriptContents = new ArrayList<>();
}
