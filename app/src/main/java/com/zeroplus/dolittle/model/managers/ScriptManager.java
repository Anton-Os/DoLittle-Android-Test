package com.zeroplus.dolittle.model.managers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.Speakers;

public class ScriptManager {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addScript(Script script, Speakers.Speaker speaker){
        scripts.add(Pair.create(script, speaker));
        if(!fullScripts.containsKey(LocalDate.now())) fullScripts.put(LocalDate.now(), new ArrayList<>());
        Objects.requireNonNull(fullScripts.get(LocalDate.now())).add(Pair.create(speaker, script));
    }

    // Callback interface for TimedEvent
    public interface Callback { void execute(); }

    @SuppressLint("NewApi")
    public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(); // test with 1 element

    public Map<LocalDate, List<Pair<Speakers.Speaker, Script>>> fullScripts = Map.of();
    // public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(Arrays.asList(Pair.create(new Script("Hello how are you?"), new Speakers.User("User")))); // test with 1 element
}