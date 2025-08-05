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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.model.Avatar
import com.zeroplus.dolittle.model.Script
import com.zeroplus.dolittle.model.managers.VoiceManager
import com.zeroplus.dolittle.ui.theme.DoLittleTheme
import com.zeroplus.dolittle.ui.theme.LightGray
import com.zeroplus.dolittle.ui.widgets.ContentPopup
import com.zeroplus.dolittle.ui.widgets.DatePopup
import com.zeroplus.dolittle.ui.widgets.ScriptContentPane
import com.zeroplus.dolittle.ui.widgets.Welcome_PopupContent
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
                Row(Modifier
                    .padding(insets)
                    .fillMaxSize()) {
                    Box(Modifier
                        .weight(0.25F)
                        .fillMaxSize()
                        .background(Color(0xFFD0BCFF))) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Button(onClick = { }) { Image(imageVector = Icons.Filled.KeyboardArrowUp) } // Menu Option
                            Column(Modifier.weight(5.0F), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(onClick = { modeIdx.value = 0 }){ Text("Y") }
                                // Menu Option
                                Divider(Modifier
                                    .fillMaxWidth()
                                    .width(1.dp))

                                for(b in 1..MainActivity.companions.size)
                                    Button(
                                        onClick = { modeIdx.value = b },
                                        colors = ButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color(0xFFEEEEEE),
                                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                            disabledContentColor = Color(0xFFEEEEEE).copy(alpha = 0.5F)
                                        )
                                    ){
                                        Text(MainActivity.companions[b - 1].getName().substring(0..0))
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
                    Box(Modifier
                        .weight(1F)
                        .fillMaxSize()
                        .background(LightGray)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Column(modifier = Modifier
                                .weight(0.15F)
                                .fillMaxWidth()){
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
                                modifier = Modifier
                                    .weight(0.85F)
                                    .fillMaxWidth()
                                    .verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                for(s in MainActivity.scriptManager.scripts)
                                    ScriptContentPane(
                                        Avatar(s.value.getName()),
                                        scriptContent = Script.ScriptContent(s.key.getScriptContents()[0].content)
                                    )
                            }
                        }
                    }

                    if(modeIdx.value != 0) // Companion Selected
                    Box(Modifier
                        .weight(0.35F)
                        .fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Text(MainActivity.companions[modeIdx.value - 1].name, Modifier.weight(0.15F))

                            Column(
                                modifier = Modifier
                                    .weight(0.85F)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for (t in MainActivity.companions[modeIdx.value - 1].getDialouges()) Text("\n" + t.content)
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

                VoiceManager.setupSpeech()
            })
        }) { isHelpShown.value = !isHelpShown.value }

        if(isDateShown.value) DatePopup(){ isDateShown.value = !isDateShown.value }
    }
}