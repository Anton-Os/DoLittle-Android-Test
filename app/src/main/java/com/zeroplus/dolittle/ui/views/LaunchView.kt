package com.zeroplus.dolittle.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.ui.theme.DoLittleTheme

import kotlinx.coroutines.delay

@Preview
@Composable
fun LaunchView(){
    LaunchedEffect(key1 = Unit, block = {
        delay(3000)
        MainActivity.navController?.navigate("signup")
    })

    DoLittleTheme {
        Box(Modifier.fillMaxSize().background(Color(0xFFD0BCFF)), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("DoLittle", fontSize = 60.sp, fontWeight = FontWeight.Bold)
                Text("Your Thoughts, Captured")
            }
        }
    }
}