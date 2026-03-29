package com.zeroplus.dolittle.model.managers

import java.time.LocalDate

class Storage {
    fun saveScriptsState(scriptManager: ScriptManager){
        // TODO: Save all conversations from scriptManager
    }

    fun loadScriptsState(date: LocalDate) : ScriptManager{
        var scriptManager = ScriptManager()

        // TODO: Load all conversations up to date

        return scriptManager
    }
}