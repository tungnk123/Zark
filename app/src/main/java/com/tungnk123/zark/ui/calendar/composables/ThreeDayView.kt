package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ThreeDayView(modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0 until 3) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = today.plusDays(i.toLong()).dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(text = today.plusDays(i.toLong()).dayOfMonth.toString())
                Spacer(modifier = Modifier.height(4.dp))
//                TimeAgendaView(modifier = Modifier.weight(1f))
            }
        }
    }
}