package com.zeroplus.dolittle.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.model.Speakers
import com.zeroplus.dolittle.model.Script
import com.zeroplus.dolittle.model.Companion
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

@Composable
fun DialougeContentPane(companion: Companion, dialouge: String, textColor : Color = OffWhite){
    var height = ((dialouge.length / 50) * 35).dp; // Scales the card based on text length

    // Card(modifier = Modifier.padding(vertical = 10.dp).fillMaxHeight(heightFrac).fillMaxWidth(0.9F),
    Card(modifier = Modifier.padding(vertical = 10.dp).height(height).fillMaxWidth(0.9F),
        colors = CardColors(
            containerColor = Color(companion.btnColorCode),
            contentColor = Color(0xFFEEEEE),
            disabledContainerColor = Color(companion.btnColorCode).copy(alpha = 0.5F),
            disabledContentColor = Color(0xAAEEEEE),
        )){

        Text(dialouge,
            Modifier.fillMaxWidth().weight(0.7F),
            color = textColor,
            textAlign = TextAlign.Center
        )
        // else display loading animation
    }
}
