package com.zeroplus.dolittle.ui.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties

import com.zeroplus.dolittle.MainActivity
import com.zeroplus.dolittle.ui.theme.OffWhite
import java.time.LocalDate

class PopupPosProvider(private val x: Int = 0, private val y: Int = 0) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y
        )
    }
}

@Composable
fun ContentPopup(color: Color = Color(0xFFfffdeb), content: @Composable() ()-> Unit, dismissCallback: ()-> Unit = {}){
    Popup(
        properties = PopupProperties(
            focusable = true,
            usePlatformDefaultWidth = true,
            excludeFromSystemGesture = false,
            dismissOnClickOutside = false,
        ),
        popupPositionProvider = PopupPosProvider(),
        onDismissRequest = dismissCallback,
    ) {
        Box(Modifier.fillMaxWidth().height(450.dp), contentAlignment = Alignment.Center) {
            Card(
                modifier = Modifier.padding(all = 10.dp).fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                colors = CardDefaults.cardColors(color),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePopup(localDate: MutableState<LocalDate>, dismissCallback: ()-> Unit = {}){
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = dismissCallback,
        confirmButton = {
            Button(onClick = {
                localDate.value = LocalDate.ofEpochDay(datePickerState.selectedDateMillis?.div((24 * 60 * 60 * 1000)) ?: 0)
                dismissCallback()
            }) {
                Text("Select Date")
            }
        },
        // dismissButton = {  }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState()),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Welcome_PopupContent(modeIdx: MutableState<Int>, dismissCallback: ()-> Unit = {}){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text(MainActivity.companions[modeIdx.value].name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.75F).padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )

        // Text("Start recording and transcribing your conversations\n\n", textAlign = TextAlign.Center)
        Image(painter = painterResource(MainActivity.companions[modeIdx.value].resID),
        // Icon(Icons.Sharp.PlayArrow,
            modifier = Modifier.weight(1.5F),
            contentDescription = "Mic Image"
        )

        Text("To " + MainActivity.companions[modeIdx.value].roleAdj + ":\n" + MainActivity.companions[modeIdx.value].roleDesc,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.0F).padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )

        Row(Modifier.weight(0.1F).padding(start = 15.dp, end = 15.dp)){
            for(c in 0..<MainActivity.companions.size)
                Box(Modifier.weight(1F)
                    .drawWithCache {
                        onDrawBehind {
                            if(c != modeIdx.value) drawCircle(color = Color(0xFFEEEEEE), radius = size.minDimension)
                        }
                    }
                )
        }

        // Text("Never miss a thought\n\n", textAlign = TextAlign.Center)
        Row(Modifier.weight(0.5F)) {
            IconButton(onClick = { if(modeIdx.value > 0) modeIdx.value-- else modeIdx.value = MainActivity.companions.size - 1 }, Modifier.padding(bottom = 20.dp)){
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back Arrow",
                )
            }
            Button(
                onClick = dismissCallback,
                modifier = Modifier.weight(1F).padding(bottom = 20.dp),
                colors = ButtonColors(
                    containerColor = Color(MainActivity.companions[modeIdx.value].btnColorCode),
                    contentColor = if(modeIdx.value < 2) MaterialTheme.colorScheme.secondary else OffWhite,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                    disabledContentColor = OffWhite.copy(alpha = 0.5F)
                )
            ) {
                Text("Start Dialouge")
            }
            IconButton(onClick = { if(modeIdx.value < MainActivity.companions.size - 1) modeIdx.value++ else modeIdx.value = 0 }, Modifier.padding(bottom = 20.dp)){
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Forward Arrow",
                )
            }
        }
    }
}