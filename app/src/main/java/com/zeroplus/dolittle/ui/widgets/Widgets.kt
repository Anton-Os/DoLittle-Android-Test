package com.zeroplus.dolittle.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.zeroplus.dolittle.model.Avatar
import com.zeroplus.dolittle.model.Script

@Composable
fun ScriptContentPane(Avatar: Avatar, scriptContent: Script.ScriptContent){
    Column(modifier = Modifier.padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(Avatar.name)

        Card(modifier = Modifier.padding(horizontal = 10.dp)){
            Text(scriptContent.content)
        }
    }
}