package com.zeroplus.dolittle.model.managers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Storage {
    // @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveScripts(ScriptManager scriptManager){
        @SuppressLint({"NewApi", "LocalSuppress"}) var localDate = LocalDate.now();

        Log.d("STATUS", "Saving scripts for date " + localDate.toString());
        File file = new File("Scripts-" + localDate.toString() + ".txt");

        // TODO: Save all conversations
    }

    @SuppressLint("NewApi")
    public ScriptManager loadScripts(){
        ScriptManager scriptManager = new ScriptManager();
        @SuppressLint({"NewApi", "LocalSuppress"}) var localDate = LocalDate.now();

        if(Files.exists(Paths.get("Scripts-" + localDate.toString() + ".txt")))
            Log.d("STATUS", "Loading file up to current date");
        else Log.d("STATUS", "No scripts detected for current date");

        // TODO: Load all conversations

        return scriptManager;
    }
}
