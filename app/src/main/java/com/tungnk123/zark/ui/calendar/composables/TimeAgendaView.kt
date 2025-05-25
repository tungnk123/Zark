package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tungnk123.zark.data.dto.calendar.CalendarDto

@Composable
fun TimeAgendaView(
    events: List<CalendarDto>,
    modifier: Modifier = Modifier,
) {
    val eventsByDate = events
        .sortedBy { it.startTime }
        .groupBy { it.startTime.toLocalDate() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        eventsByDate.forEach { (date, dayEvents) ->
            item {
                Text(
                    text = date.toString(),
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(dayEvents.size) { index ->
                val event = dayEvents[index]

                TaskItem(
                    title = event.title,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}