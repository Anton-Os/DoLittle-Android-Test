package com.zeroplus.dolittle.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.model.managers.PermManager
import com.zeroplus.dolittle.ui.theme.DoLittleTheme

@Preview
@Composable
fun OnboardView(){
    val isBackgroundGranted = PermManager.granted(PermManager.Perm.BACKGROUND_PERMS)
    val isMicrophoneGranted = PermManager.granted(PermManager.Perm.RECORD_AUDIO)
    val isStorageGranted = PermManager.granted(PermManager.Perm.READ_STORAGE)
    val isNotificationGranted = PermManager.granted(PermManager.Perm.POST_NOTIFICATIONS)

    LaunchedEffect(key1 = Unit, block = {
        if(isBackgroundGranted && isMicrophoneGranted && isStorageGranted && isNotificationGranted)
            MainActivity.navController?.navigate("home") // No need to reaccept permissions
    })

    var acceptedPerms : Map<Pair<String, Array<String>>, MutableState<Boolean>> = mapOf(
        Pair("Privacy", PermManager.Perm.BACKGROUND_PERMS) to remember { mutableStateOf(isBackgroundGranted) },
        Pair("Microphone", arrayOf(PermManager.Perm.RECORD_AUDIO)) to remember { mutableStateOf(isMicrophoneGranted) },
        Pair("Storage", arrayOf(PermManager.Perm.READ_STORAGE)) to remember { mutableStateOf(isStorageGranted) },
        Pair("Notifications", arrayOf(PermManager.Perm.POST_NOTIFICATIONS)) to remember { mutableStateOf(isNotificationGranted) }
    )

    DoLittleTheme {
        Box(Modifier.fillMaxSize().background(Color(0xFFD0BCFF))) {
            Column(Modifier.fillMaxSize()) {
                // Text("DoLittle", Modifier.weight(0.1F), fontSize = 40.sp, fontWeight = FontWeight.Bold)
                Text(
                    "\n\nTo use the app, please accept the required permissions and sign in:",
                    Modifier.weight(0.25F),
                    textAlign = TextAlign.Center
                )
                Box(Modifier.weight(1F).fillMaxWidth().background(Color(0xFFfffdeb)).border(width = 2.dp, color = Color(0xAA0000FF))) {
                    Column() {
                        Box(Modifier.weight(1F))
                        for (p in acceptedPerms)
                            Row(Modifier.weight(1F),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                checked = p.value.value,
                                onCheckedChange = {
                                    PermManager.request(p.key.second) { isGranted ->
                                        p.value.value = isGranted == PermManager.RequestResult.GRANTED
                                    }
                                    p.value.value = true // for testing
                                },
                                )
                                Text(p.key.first)
                            }
                        Box(Modifier.weight(1F))
                    }
                }
                Divider(Modifier.fillMaxWidth().width(1.dp), color = Color.Blue)
                Column(Modifier.weight(0.4F), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.weight(0.5F))
                    Box(Modifier.fillMaxWidth().weight(1F), contentAlignment = Alignment.Center) {
                        Button(onClick = { MainActivity.navController?.navigate("home") }) {
                            Text("Sign Up")
                        }
                    }
                    Box(Modifier.fillMaxWidth().weight(1F), contentAlignment = Alignment.Center) {
                        TextButton(onClick = { MainActivity.navController?.navigate("home") }) {
                            Text("Continue as Guest")
                        }
                    }
                    Box(Modifier.weight(0.5F))
                }
            }
        }
    }
}