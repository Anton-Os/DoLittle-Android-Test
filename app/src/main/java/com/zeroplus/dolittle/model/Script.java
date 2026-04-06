package com.zeroplus.dolittle.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Script {
    // public Script(ArrayList<ScriptContent> scriptContentsArg){
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Script(String textArg){
        // scriptContents = scriptContentsArg;
        scriptContents.add(new Content(textArg));
    }
    public static class Content {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public Content(String contentArg){ content = contentArg; }

        public String getContent(){ return content; }
        private String content;
    }

    public ArrayList<Content> getScriptContents(){ return scriptContents; }

    public String getPrintedScript(){
        StringBuilder printedScript = new StringBuilder();
        for(Content s : scriptContents) printedScript.append(s.getContent());
        return printedScript.toString();
    }
    private ArrayList<Content> scriptContents = new ArrayList<>();
}
