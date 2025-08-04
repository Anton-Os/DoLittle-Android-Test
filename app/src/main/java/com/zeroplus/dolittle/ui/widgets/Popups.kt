package com.zeroplus.dolittle.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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

import com.zeroplus.dolittle.R

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
fun ContentPopup(content: @Composable() ()-> Unit, dismissCallback: ()-> Unit = {}){
    Popup(
        properties = PopupProperties(
            focusable = true,
            usePlatformDefaultWidth = true,
            excludeFromSystemGesture = false,
            dismissOnClickOutside = true,
        ),
        popupPositionProvider = PopupPosProvider(),
        onDismissRequest = dismissCallback,
    ) {
        Box(Modifier.fillMaxWidth().height(450.dp), contentAlignment = Alignment.Center) {
            Card(
                modifier = Modifier.padding(all = 10.dp).fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                colors = CardDefaults.cardColors(Color(0xFFfffdeb)),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePopup(dismissCallback: ()-> Unit = {}){
    val datePickerState = rememberDatePickerState()
    // val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        onDismissRequest = dismissCallback,
        confirmButton = {
            Button(onClick = { dismissCallback() }) {
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

@Composable
fun Welcome_PopupContent(dismissCallback: ()-> Unit = {}){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text("\nWelcome! Start capturing your thoughts and gain valuable insights about yourself using AI",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.25F)
        )

        // Text("Start recording and transcribing your conversations\n\n", textAlign = TextAlign.Center)
        Image(painter = painterResource(R.drawable.baked_goods_1),
        // Icon(Icons.Sharp.PlayArrow,
            modifier = Modifier.weight(2F),
            contentDescription = "Device Image"
        )

        // Text("Never miss a thought\n\n", textAlign = TextAlign.Center)
        Button(onClick = dismissCallback, modifier = Modifier.weight(0.5F)) {
            Text("Begin!")
        }
    }
}