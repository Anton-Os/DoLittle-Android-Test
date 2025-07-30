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
        public ScriptContent(String contentArg){
            timestamp = LocalDate.now();
            content = contentArg;
        }

        public LocalDate getTimestamp(){ return timestamp; }
        LocalDate timestamp;

        public String getContent(){ return content; }
        private String content;
    }

    public ArrayList<ScriptContent> getScriptContents(){ return scriptContents; }
    private ArrayList<ScriptContent> scriptContents;
}
