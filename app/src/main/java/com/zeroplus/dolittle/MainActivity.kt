package com.zeroplus.dolittle

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zeroplus.dolittle.model.companions.Angel
import com.zeroplus.dolittle.model.companions.Demon
import com.zeroplus.dolittle.model.companions.Ghost
import com.zeroplus.dolittle.model.managers.PermManager
import com.zeroplus.dolittle.model.managers.ScriptManager
import com.zeroplus.dolittle.model.managers.VoiceManager
import com.zeroplus.dolittle.ui.views.HomeView
import com.zeroplus.dolittle.ui.views.LaunchView
import com.zeroplus.dolittle.ui.views.OnboardView
import com.zeroplus.dolittle.ui.views.SettingsView

var activity : ComponentActivity? = null

class MainActivity : ComponentActivity() {
    init { activity = this }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // voiceManager = VoiceManager(this)
        // createNotificationChannel("com.dolittle.notification", "DoLittle is here!")

        setContent {
            navController = rememberNavController()

            NavHost(navController = navController!!, startDestination = "launch") {
                composable(route = "launch") { LaunchView() }
                composable(route = "signup") {
                    BackHandler(true){ }
                    OnboardView()
                }
                composable(route = "settings") { SettingsView() }
                composable(
                    route = "home" // + "?text={text}",
                    /* arguments = listOf(
                        navArgument("text") {
                            type = NavType.StringType
                            nullable = true
                        }
                    ) */
                ) {
                    BackHandler(true){ }
                    HomeView() // TODO: Add argument
                }
            }

            navController?.navigate("launch")
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume() triggered")

        // if(!PermManager.granted(arrayOf(PermManager.Perm.recordAudio))) println("Permission not granted!")
    }

    override fun onDestroy() {
        // voiceManager.speechRecognizer.destroy()
        super.onDestroy()
    }

    fun createNotificationChannel(channelID : String, channelName : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "DoLittle to send reminders to user"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var navController : NavHostController? = null

        @kotlin.jvm.JvmField
        var permManager : PermManager = PermManager()
        lateinit var notificationChannel : NotificationChannel

        lateinit var voiceManager: VoiceManager
        @kotlin.jvm.JvmField
        var scriptManager : ScriptManager = ScriptManager()
        @RequiresApi(Build.VERSION_CODES.O)
        var companions : List<com.zeroplus.dolittle.model.Companion> = listOf(
            Angel(), Ghost(), Demon()
        )
    }
}