package com.zeroplus.dolittle.model.managers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.Speakers;

public class ScriptManager {

    /* public ScriptManager() {
        loadScripts(""); // test
    } */

    @SuppressLint("NewApi")
    public void loadScripts(String filePath) {
        if(!filePath.isEmpty()) {
            StringBuilder strBuilder = new StringBuilder();

            File scriptFile = new File(filePath);
            try(FileReader reader = new FileReader(scriptFile)){
                int readChar = reader.read();
                while(readChar != -1) {
                    strBuilder.append(readChar);
                    readChar = reader.read();
                }
            } catch (IOException e) {
                Log.e("ScriptManager", "Error loading scripts: " + e.getMessage());
            }

            // TODO: Parse built String and append data
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public void saveScripts(String filePath) {
        File scriptFile = new File(filePath);
        try (FileWriter writer = new FileWriter(scriptFile, true)) { // true for append mode
            for (var entry : scripts) {
                writer.write("Avatar: " + entry.second.getName() + "\n");
                for (Script.ScriptContent c : entry.first.getScriptContents())
                    writer.write(c.getTimestamp() + ":" + c.getContent() + "\n");
            }
        } catch (IOException e) {
            Log.e("ScriptManager", "Error saving scripts: " + e.getMessage());
        }
    }

    public void addScript(Script script, Speakers.Speaker speaker){ scripts.add(Pair.create(script, speaker)); }

    // Callback interface for TimedEvent
    public interface Callback { void execute(); }

    @SuppressLint("NewApi")
    public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(); // test with 1 element
    // public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(Arrays.asList(Pair.create(new Script("Hello how are you?"), new Speakers.User("User")))); // test with 1 element
}