package com.zeroplus.dolittle.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.zeroplus.dolittle.model.Speakers
import com.zeroplus.dolittle.model.Script
import com.zeroplus.dolittle.ui.theme.OffGrey
import com.zeroplus.dolittle.ui.theme.OffWhite

@Composable
fun ScriptContentPane(speaker: Speakers.Speaker, scriptContent: Script.Content){
    Column(modifier = Modifier.padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(speaker.name)

        Card(
            modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(0.9F),
            colors = CardColors(
                containerColor = OffWhite,
                contentColor = OffGrey,
                disabledContainerColor = OffWhite.copy(alpha = 0.5F),
                disabledContentColor = OffGrey.copy(alpha = 0.5F)
            )
        ){
            Text(scriptContent.content, textAlign = TextAlign.Center)
        }
    }
}