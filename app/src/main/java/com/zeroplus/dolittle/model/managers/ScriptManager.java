package com.zeroplus.dolittle.model.managers;

import android.annotation.SuppressLint;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

import com.zeroplus.dolittle.model.Script;
import com.zeroplus.dolittle.model.Speakers;

public class ScriptManager {

    public void addScript(Script script, Speakers.Speaker speaker){ scripts.add(Pair.create(script, speaker)); }

    // Callback interface for TimedEvent
    public interface Callback { void execute(); }

    @SuppressLint("NewApi")
    public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(); // test with 1 element

    // public List<Pair<Script, Speakers.Speaker>> scripts = new ArrayList<>(Arrays.asList(Pair.create(new Script("Hello how are you?"), new Speakers.User("User")))); // test with 1 element
}