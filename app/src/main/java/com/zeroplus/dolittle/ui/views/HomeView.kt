package com.zeroplus.dolittle.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { isHelpShown.value = !isHelpShown.value },
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color(0xAAEEEEEE)
                    // ) {  }
                    ) { Icon(Icons.Sharp.MoreVert, "Floating action button.") }
                }
            ) { insets ->
                Row(Modifier.padding(insets).fillMaxSize()) {
                    Box(Modifier.weight(0.25F).fillMaxSize().background(Color(0xFFD0BCFF))) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Button(onClick = { }) { Image(imageVector = Icons.Filled.KeyboardArrowUp) } // Menu Option
                            Column(Modifier.weight(5.0F), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(
                                    onClick = { modeIdx.value = 0 },
                                    colors = ButtonColors(
                                        containerColor = if(modeIdx.value != 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                        contentColor = if(modeIdx.value != 0) Color(0xFFEEEEEE) else Color(0xFF0000FF),
                                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                        disabledContentColor = Color(0xFFEEEEEE).copy(alpha = 0.5F)
                                    )
                                ){
                                    Image(painterResource(R.drawable.brain), "Companion Image")
                                }
                                // Menu Option
                                Divider(Modifier.fillMaxWidth().width(1.dp))

                                for(b in 1..MainActivity.companions.size)
                                    Button(
                                        onClick = {
                                            modeIdx.value = b
                                            val script = MainActivity.scriptManager.scripts.keys.last()
                                            if(script != null) MainActivity.companions[b - 1].getAdvice(script.printedScript)
                                        },
                                        colors = ButtonColors(
                                            containerColor = if(modeIdx.value != b) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                            contentColor = if(modeIdx.value != b) Color(0xFFEEEEEE) else Color(0xFF0000FF),
                                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                            disabledContentColor = Color(0xFFEEEEEE).copy(alpha = 0.5F)
                                        )
                                    ){
                                        Image(painterResource(MainActivity.companions[b - 1].resID), "Companion Image")
                                    } // Menu Option
                            }
                            Switch(
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
                            }
                        }
                    }
                    Box(Modifier.weight(1F).fillMaxSize().background(LightGray)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Column(modifier = Modifier.weight(0.15F).fillMaxWidth()){
                                Text("Now:", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                Row(verticalAlignment = Alignment.CenterVertically){
                                    Text(LocalDate.now().monthValue.toString() + " /", Modifier.weight(1F), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                    Text(LocalDate.now().dayOfMonth.toString() + " /", Modifier.weight(1F), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    // Text("|", Modifier.weight(1F), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                    Text(LocalDate.now().year.toString(), Modifier.weight(1F), fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                    IconButton(
                                        modifier = Modifier.weight(0.75F),
                                        onClick = { isDateShown.value = !isDateShown.value },
                                        enabled = true,
                                    ) {
                                        Icon(Icons.Sharp.List, "List Dates")
                                    }
                                }
                            }

                            val scrollState = rememberScrollState()
                            Column(
                                modifier = Modifier.weight(1F).fillMaxWidth().verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                for(s in MainActivity.scriptManager.scripts)
                                    ScriptContentPane(
                                        Avatar(s.value.getName()),
                                        scriptContent = Script.ScriptContent(s.key.getScriptContents()[0].content)
                                    )
                            }

                            TextField(
                                value = inputTxt.value,
                                onValueChange = { inputTxt.value = it },
                                modifier = Modifier.weight(0.1F)
                            )
                        }
                    }

                    if(modeIdx.value != 0) // Companion Selected
                    Box(Modifier.weight(0.35F).fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text(MainActivity.companions[modeIdx.value - 1].name, Modifier.weight(0.1F))

                            val scrollState = rememberScrollState()
                            Column(
                                modifier = Modifier.weight(0.9F).fillMaxWidth().verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for (t in MainActivity.companions[modeIdx.value - 1].getDialouges())
                                    Text("\n" + t.content, style = typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

        if(isHelpShown.value) ContentPopup({
            Welcome_PopupContent({
                isHelpShown.value = !isHelpShown.value
                val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
                val timestamp : String = formatter.format( Date())
                val outputFile : File = File(activity?.filesDir, "Audio" + timestamp + ".3gp") // ".mp3")
                println("Using audio file " + outputFile.absolutePath + ", from dir " + activity?.filesDir)

                // VoiceManager.setupSpeech(outputFile.absolutePath)
            })
        }) { isHelpShown.value = !isHelpShown.value }

        if(isDateShown.value) DatePopup(){ isDateShown.value = !isDateShown.value }
    }
}