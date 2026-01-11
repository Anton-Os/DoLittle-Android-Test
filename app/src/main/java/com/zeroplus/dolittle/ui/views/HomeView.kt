package com.zeroplus.dolittle.ui.views

import android.os.Build
import android.view.KeyEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.R
import com.zeroplus.dolittle.activity
import com.zeroplus.dolittle.model.Avatar
import com.zeroplus.dolittle.model.Script
import com.zeroplus.dolittle.ui.theme.DoLittleTheme
import com.zeroplus.dolittle.ui.theme.LightGray
import com.zeroplus.dolittle.ui.theme.OffWhite
import com.zeroplus.dolittle.ui.theme.typography
import com.zeroplus.dolittle.ui.widgets.ContentPopup
import com.zeroplus.dolittle.ui.widgets.DatePopup
import com.zeroplus.dolittle.ui.widgets.ScriptContentPane
import com.zeroplus.dolittle.ui.widgets.Welcome_PopupContent
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeView(){
    val isLightTheme = remember { mutableStateOf(true) }
    val isHelpShown = remember { mutableStateOf(true) } // TODO: Make this context dependent
    val isDateShown = remember { mutableStateOf(false) }

    val modeIdx = remember { mutableStateOf(0) }

    var inputTxt = remember { mutableStateOf("What should I do today?") }

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
                     Box(Modifier.weight(1F).fillMaxSize().background(
                         if(modeIdx.value < 1) Color(0xFFfffef0) else Color(MainActivity.companions[modeIdx.value - 1].colorCode)
                     )) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Column(modifier = Modifier.weight(0.15F).fillMaxWidth()){
                                Text("Now:", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                Row(verticalAlignment = Alignment.CenterVertically){
                                    Text(LocalDate.now().monthValue.toString() + " /", Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                    Text(LocalDate.now().dayOfMonth.toString() + " /", Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                    Text(LocalDate.now().year.toString(), Modifier.weight(1F), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    IconButton(
                                        modifier = Modifier.weight(0.75F),
                                        onClick = { isDateShown.value = !isDateShown.value },
                                        enabled = true,
                                    ) {
                                        Icon(Icons.Sharp.List, "List Dates")
                                    }
                                }
                            }

                            Box(Modifier.weight(1F).fillMaxWidth()) {
                                val scrollState = rememberScrollState()
                                Column(
                                    modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    for (s in MainActivity.scriptManager.scripts)
                                        ScriptContentPane(
                                            Avatar(s.value.getName()),
                                            scriptContent = Script.ScriptContent(s.key.getScriptContents()[0].content)
                                        )
                                }

                                Box(){
                                    // TODO: Companion here
                                }
                            }

                            // val focusRequester = remember { FocusRequester() }

                            /* TextField(
                                // value = inputTxt.value,
                                // onValueChange = { inputTxt.value = it },
                                // modifier = Modifier.weight(0.15F),
                                /* keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        MainActivity.scriptManager.addScript(Script(inputTxt.value), Avatar("Me"))
                                        focusRequester.requestFocus()
                                    }
                                ),
                                modifier = Modifier.weight(0.1F).onKeyEvent {
                                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
                                        focusRequester.requestFocus()
                                        true
                                    }
                                    false
                                } */
                            ) */
                        }
                    }

                    // if(modeIdx.value != 0) // Companion Selected
                    /* Box(Modifier.weight(0.225F).fillMaxSize().background(Color(MainActivity.companions[modeIdx.value - 1].getColorCode())), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Box(Modifier.fillMaxSize().weight(0.05F), contentAlignment = Alignment.Center){ // .background(MainActivity.companions[modeIdx.value - 1].color)) {
                                Text(MainActivity.companions[modeIdx.value - 1].name, textAlign = TextAlign.Center)
                            }

                            val scrollState = rememberScrollState()
                            Column(
                                modifier = Modifier.weight(0.9F).fillMaxWidth().verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for (t in MainActivity.companions[modeIdx.value - 1].getDialouges())
                                    Column() {
                                        Card() { Text("\n" + t.content, style = typography.bodySmall) }
                                        Spacer(Modifier.height(10.dp))
                                    }
                            }
                        }
                    } */

                    Box(Modifier.weight(0.1F).fillMaxSize().padding(horizontal = 20.dp)) {
                         Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                             // Button(onClick = { }) { Image(imageVector = Icons.Filled.KeyboardArrowUp) } // Menu Option
                             Row(Modifier.weight(5.0F)) { // horizontalAlignment = Alignment.CenterHorizontally) {
                                 Button(
                                     onClick = { modeIdx.value = 0 },
                                     Modifier.weight(1.0F),
                                     border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                                     colors = ButtonColors(
                                         containerColor = if(modeIdx.value != 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                         contentColor = if(modeIdx.value != 0) OffWhite else MaterialTheme.colorScheme.secondary,
                                         disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                         disabledContentColor = OffWhite.copy(alpha = 0.5F)
                                     )
                                 ){
                                     Image(painterResource(R.drawable.brain), "Companion Image")
                                 }
                                 // Menu Option
                                 // Divider(Modifier.fillMaxWidth().width(1.dp))

                                 for(b in 1..MainActivity.companions.size)
                                     Button(
                                         onClick = { modeIdx.value = b },
                                         Modifier.weight(1.0F),
                                         border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                                         colors = ButtonColors(
                                             containerColor = if(modeIdx.value != b) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                             contentColor = if(modeIdx.value != b) OffWhite else MaterialTheme.colorScheme.secondary,
                                             disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                             disabledContentColor = OffWhite.copy(alpha = 0.5F)
                                         )
                                     ){
                                         Image(painterResource(MainActivity.companions[b - 1].resID), "Companion Image")
                                     } // Menu Option
                             }
                             /* Switch(
                                 modifier = Modifier.weight(0.75F),
                                 checked = isLightTheme.value,
                                 onCheckedChange = { isLightTheme.value = it }
                             )
                             IconButton(
                                 modifier = Modifier.weight(0.75F),
                                 onClick = { MainActivity.navController?.navigate("settings") },
                                 enabled = true,
                             ) {
                                 Icon(Icons.Sharp.Settings, "Information")
                             } */
                         }
                     }
                 }
            }
        }

        if(isHelpShown.value)
            ContentPopup({
                Welcome_PopupContent({
                    isHelpShown.value = !isHelpShown.value
                    val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
                    val timestamp : String = formatter.format( Date())
                    val outputFile : File = File(activity?.filesDir, "Audio" + timestamp + ".3gp") // ".mp3")
                    println("Using audio file " + outputFile.absolutePath + ", from dir " + activity?.filesDir)

                    // VoiceManager.setupSpeech(outputFile.absolutePath)
                })
            }) { isHelpShown.value = !isHelpShown.value }

        if(isDateShown.value) DatePopup(){
            isDateShown.value = !isDateShown.value
        }
    }
}