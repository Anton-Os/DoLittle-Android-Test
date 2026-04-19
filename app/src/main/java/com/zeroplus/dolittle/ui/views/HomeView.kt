package com.zeroplus.dolittle.ui.views

import android.annotation.SuppressLint
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.model.Companion
import com.zeroplus.dolittle.activity
import com.zeroplus.dolittle.ui.theme.DoLittleTheme
import com.zeroplus.dolittle.ui.theme.OffWhite
import com.zeroplus.dolittle.model.managers.VoiceManager
import com.zeroplus.dolittle.ui.theme.OffGrey
import androidx.compose.ui.graphics.drawscope.rotate
import com.zeroplus.dolittle.ui.widgets.ContentPopup
import com.zeroplus.dolittle.ui.widgets.DatePopup
import com.zeroplus.dolittle.ui.widgets.DialougeContentPane
import com.zeroplus.dolittle.ui.widgets.ScriptContentPane
import com.zeroplus.dolittle.ui.widgets.Welcome_PopupContent

var savedModeIdx : Int = 0

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeView() {
    val isHelpShown = remember { mutableStateOf(true) } // TODO: Make this context dependent
    val isDateShown = remember { mutableStateOf(false) }

    val modeIdx = remember { mutableStateOf(savedModeIdx) }

    val dialouge = remember { mutableStateOf("") }
    val scripts = remember { mutableStateOf(MainActivity.scriptManager.scripts.toMutableList()) }
    val localDate = remember { mutableStateOf(LocalDate.now()) }

    fun record(callback: VoiceManager.ListenerCallback) = run {
        if(!VoiceManager.getIsListening()) // protects from double firing
            VoiceManager.listen(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
            }, callback)
    }

    fun getCompanion() : Companion { return MainActivity.companions[modeIdx.value] }

    // TODO: Add Disposable Effect

    DoLittleTheme {
        ModalNavigationDrawer(
            drawerContent = {},
            gesturesEnabled = false
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                /* floatingActionButton = {
                    if(modeIdx.value != 0)
                        FloatingActionButton(
                            onClick = {
                                val script = MainActivity.scriptManager.scripts.keys.last()
                                if(script != null) MainActivity.companions[modeIdx.value - 1].getAdvice(script.printedScript)
                            },
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = Color(0xAAEEEEEE)
                        // ) {  }
                        ) { Icon(Icons.Sharp.Add, "Floating action button.") }
                } */
            ) { insets ->
                Column(Modifier.padding(insets).fillMaxSize()) {
                    // Box(Modifier.weight(if(modeIdx.value != 0) 0.775F else 1F).fillMaxSize().background(LightGray)) {
                    Box(Modifier.weight(1F).fillMaxSize().background(Color(getCompanion().litColorCode))) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(Modifier.weight(0.115F).fillMaxWidth().background(Color(getCompanion().altColorCode).copy(alpha = 0.5F))) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text("Now:", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(modifier = Modifier.weight(0.75F), onClick = { isDateShown.value = !isDateShown.value }, enabled = true){
                                                Icon(Icons.Sharp.List, "List Dates")
                                            }
                                            Text(localDate.value.monthValue.toString() + " /", Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                            // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                            Text(localDate.value.dayOfMonth.toString() + " /", Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                            // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                            Text(localDate.value.year.toString(), Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                            IconButton(modifier = Modifier.weight(0.75F), onClick = { MainActivity.navController?.navigate("settings") }, enabled = true,) {
                                                Icon(Icons.Sharp.Settings, "Open Settings")
                                            }
                                    }
                                }
                            }

                            if(scripts.value.isNotEmpty())
                                Box(Modifier.weight(1F).fillMaxWidth()) {
                                val scrollState = rememberScrollState()
                                Column(
                                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    for (s in scripts.value) {
                                        ScriptContentPane(
                                            s.second,
                                            scriptContent = s.first.scriptContents[0]
                                        )

                                        val dialougeResponse = getCompanion().dialouges.get(s.first.scriptContents[0])
                                        if(dialougeResponse != null && dialougeResponse != dialouge.value)
                                            DialougeContentPane(getCompanion(), dialougeResponse, if(modeIdx.value < 2) OffGrey else OffWhite)
                                    }

                                    if(dialouge.value.isNotEmpty()) Spacer(Modifier.height(50.dp)) // Extra whitespace
                                }

                                if(dialouge.value.isNotEmpty())
                                Box(Modifier.align(Alignment.BottomCenter)){
                                    Row(Modifier.fillMaxWidth(0.75F)){
                                        for(b in 0..<MainActivity.companions.size)
                                            if(b == modeIdx.value) Box(Modifier.fillMaxSize(0.05F)
                                                .drawWithCache {
                                                    val roundedPolygon = RoundedPolygon(
                                                        numVertices = 3,
                                                        radius = (size.minDimension / 2) + (size.minDimension / 5),
                                                        centerX = (size.width / 4) * 3,
                                                        centerY = (size.height / 4) * 3
                                                    )
                                                    val roundedPolygonPath = roundedPolygon.toPath().asComposePath()

                                                    onDrawBehind {
                                                        rotate(degrees = 90F){
                                                            drawPath(roundedPolygonPath, color = Color(getCompanion().btnColorCode))
                                                        }
                                                    }
                                                }
                                            )
                                            else Spacer(Modifier.weight(1F))
                                    }
                                }

                                if(dialouge.value.isNotEmpty())
                                Box(Modifier.align(Alignment.BottomCenter)){
                                    DialougeContentPane(getCompanion(), dialouge.value, if(modeIdx.value < 2) OffGrey else OffWhite)
                                }
                            }
                            else Box(Modifier.weight(1F).fillMaxWidth()){
                                Box(Modifier.align(Alignment.Center)){
                                    Text("Start talking\n\nto trigger\n\nLive Interaction", fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }

                    Box(Modifier.weight(0.1F).fillMaxSize().background(Color(getCompanion().altColorCode)).padding(horizontal = 20.dp)) {
                        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            // Button(onClick = { }) { Image(imageVector = Icons.Filled.KeyboardArrowUp) } // Menu Option
                            Spacer(Modifier.weight(1.0F))
                            Row(Modifier.weight(5.0F)) { // horizontalAlignment = Alignment.CenterHorizontally) {
                                for(b in 0..<MainActivity.companions.size)
                                    Button(
                                        onClick = {
                                            modeIdx.value = b
                                            dialouge.value = "\nHello world, I am ${getCompanion().name}"
                                            savedModeIdx = modeIdx.value
                                            record { isListening ->
                                                scripts.value = MainActivity.scriptManager.scripts.toMutableList()
                                                if(scripts.value.isNotEmpty()) getCompanion().getAdvice(scripts.value.last().first.scriptContents[0], dialouge) // Test Advice Logic
                                            }
                                        },
                                        Modifier.weight(1.0F).padding(horizontal = 5.dp),
                                        border = BorderStroke(
                                            width = if(modeIdx.value != b) 1.dp else 1.5.dp,
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = if(modeIdx.value != b) 0.25F else 1.0F)
                                        ),
                                        colors = ButtonColors(
                                            containerColor = if(modeIdx.value != b) Color.Transparent else Color(MainActivity.companions[b].btnColorCode),
                                            contentColor = if(modeIdx.value != b) OffWhite else MaterialTheme.colorScheme.secondary,
                                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                            disabledContentColor = OffWhite.copy(alpha = 0.5F)
                                        )
                                    ){
                                        Image(painterResource(MainActivity.companions[b].resID), "Companion Image")
                                    } // Menu Option
                            }
                            Spacer(Modifier.weight(1.0F))
                        }
                    }
                }
            }
        }

        if(isHelpShown.value)
            ContentPopup(color = Color(getCompanion().litColorCode), {
                Welcome_PopupContent(modeIdx, {
                    isHelpShown.value = !isHelpShown.value
                    val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
                    val timestamp : String = formatter.format( Date())
                    val outputFile : File = File(activity?.filesDir, "Audio" + timestamp + ".3gp") // ".mp3")
                    println("Using audio file " + outputFile.absolutePath + ", from dir " + activity?.filesDir)

                    record { isListening ->
                        scripts.value = MainActivity.scriptManager.scripts.toMutableList()
                        if(scripts.value.isNotEmpty()) getCompanion().getAdvice(scripts.value.last().first.scriptContents[0], dialouge) // Test Advice Logic
                    }
                })
            }) { isHelpShown.value = !isHelpShown.value }

        if(isDateShown.value) DatePopup(localDate){
            isDateShown.value = !isDateShown.value
        }
    }
}
