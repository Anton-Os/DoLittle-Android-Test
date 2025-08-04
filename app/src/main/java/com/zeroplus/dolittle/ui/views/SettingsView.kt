package com.zeroplus.dolittle.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsView(){
    Box(Modifier.fillMaxSize().background(Color(0xFFD0BCFF)), contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxSize()){
            Box(Modifier.weight(1F)){
                Column() {
                    Text("General", Modifier.fillMaxWidth(), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
            Divider(Modifier.fillMaxWidth().width(1.dp), color = Color.Blue)
            Box(Modifier.weight(1F)){
                Column() {
                    Text("Avatars", Modifier.fillMaxWidth(), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
            Divider(Modifier.fillMaxWidth().width(1.dp), color = Color.Blue)
            Box(Modifier.weight(1F)){
                Column() {
                    Text("Companions", Modifier.fillMaxWidth(), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
            Divider(Modifier.fillMaxWidth().width(1.dp), color = Color.Blue)
        }
    }
}