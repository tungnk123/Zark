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
import androidx.compose.foundation.lazy.rememberLazyListState
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
    val listState = rememberLazyListState()

    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(60_000L)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(24) { hour ->
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
                            color = Color.LightGray
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
                }
            }
        }

        events.forEach { event ->
            val eventHour = event.startTime.hour
            val eventMinute = event.startTime.minute

            val absolutePositionFromTop = with(LocalDensity.current) {
                (eventHour * hourHeight.value + (eventMinute / 60f) * hourHeight.value).dp
            }

            val scrollOffsetDp = with(LocalDensity.current) {
                val firstVisibleItemOffset = listState.firstVisibleItemScrollOffset.toDp()
                val firstVisibleItemPosition = (listState.firstVisibleItemIndex * hourHeight.value).dp
                firstVisibleItemPosition + firstVisibleItemOffset
            }

            val finalYPosition = absolutePositionFromTop - scrollOffsetDp

            val isInVisibleArea = finalYPosition > -100.dp && finalYPosition < 800.dp // Adjust buffer as needed

            if (isInVisibleArea) {
                Box(
                    modifier = Modifier
                        .offset(
                            x = 76.dp,
                            y = finalYPosition
                        )
                        .padding(end = 32.dp)
                ) {
                    TaskItem(
                        title = event.title,
                        isDone = event.status == true,
                        onItemClick = { onEventClick(event) }
                    )
                }
            }
        }
    }
}
