package com.zeroplus.dolittle.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.R
import com.zeroplus.dolittle.model.Speakers
import com.zeroplus.dolittle.model.Companion

@Composable
fun GeneralSettingsView(){
    Text("\nThis is an app that acts as your personal assistant, automatic notetaker, and thought analyzer.",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CompanionsSettingsView(companions: List<Companion>){
    LazyRow(Modifier.fillMaxSize()){
        items(companions){ item ->
            Card(){
                // TODO: Include a card for each companion
            }
        }
    }
}

@Composable
fun AvatarsSettingsView(speakers: List<Speakers.Speaker>){
    LazyRow(Modifier.fillMaxSize()){
        items(speakers){ item ->
            Card(){
                // TODO: Include a card for each avatar
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsView(){
    Box(Modifier.fillMaxSize().background(Color(0xFFD0BCFF)), contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxSize()){
            for(i in 0..2) // Iterates through 3 sections
            Box(Modifier.weight(0.5F)){
                Column() {
                    Text(if(i == 1) "Companions"
                         else if(i == 2) "Avatars"
                         else "General",
                        Modifier.fillMaxWidth().weight(0.15F).padding(top = 5.dp),
                        fontFamily = FontFamily(Font(R.font.josefin_slab)),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Box(Modifier.weight(0.85F).padding(all = 10.dp).background(Color(0xFFEEEEEE)).border(width = 2.dp, color = Color(0xAA0000ff))){
                        if(i == 1) CompanionsSettingsView(MainActivity.companions)
                        else if(i == 2) AvatarsSettingsView(listOf()) // TODO: Include avatars
                        else GeneralSettingsView()
                    }
                }
            }
        }
    }
}