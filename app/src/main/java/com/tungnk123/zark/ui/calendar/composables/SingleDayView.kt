package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tungnk123.zark.data.dto.calendar.EventDetail
import kotlinx.coroutines.delay
import java.time.LocalTime

@Composable
fun SingleDayView(
    events: List<EventDetail>,
    onEventClick: (EventDetail) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hourHeight = 60.dp

    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(60_000L)
        }
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(24) { hour ->
            val eventsInHour = events.filter { it.startTime.hour == hour }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(hourHeight)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "%02d:00".format(hour),
                        modifier = Modifier.width(60.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = Color.Black
                    )
                }

                if (hour == currentTime.hour) {
                    val minuteOffset = currentTime.minute
                    val offsetY = with(LocalDensity.current) {
                        (minuteOffset / 60f) * hourHeight.toPx()
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .offset(
                                y = Dp(offsetY / LocalDensity.current.density),
                                x = 60.dp
                            )
                            .background(Color.Red)
                    )
                }
                eventsInHour.forEach { event ->
                    val minuteOffset = event.startTime.minute
                    val offsetY = with(LocalDensity.current) {
                        (minuteOffset / 60f) * hourHeight.toPx()
                    }

                    Box(
                        modifier = Modifier
                            .padding(
                                start = 60.dp
                            )
                            .offset(y = Dp(offsetY / LocalDensity.current.density))
                    ) {
                        TaskItem(
                            title = event.title,
                            onItemClick = { onEventClick(event) }
                        )
                    }
                }
            }
        }
    }
}