package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tungnk123.zark.data.dto.calendar.EventDetail
import kotlinx.coroutines.delay
import java.time.LocalTime

@Composable
fun SingleDayView(
    events: List<EventDetail>,
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

    val density = LocalDensity.current

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(24) { hour ->
            val eventsInHour = events.filter { it.startTime.hour == hour }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(hourHeight)
                    .padding(horizontal = 16.dp)
            ) {
                // Thời gian bên trái
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
                }

                // Các task trong giờ hiện tại
                eventsInHour.forEach { event ->
                    val minuteOffset = event.startTime.minute
                    val offsetY = with(density) {
                        (minuteOffset / 60f) * hourHeight.toPx()
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 60.dp)
                            .offset(y = Dp(offsetY / density.density))
                            .zIndex(1f) // đảm bảo vẽ trên divider
                    ) {
                        TaskItem(title = event.title)
                    }
                }

                // Vẽ dòng thời gian hiện tại nếu khớp giờ
                if (hour == currentTime.hour) {
                    val minuteOffset = currentTime.minute
                    val offsetY = with(density) {
                        (minuteOffset / 60f) * hourHeight.toPx()
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .offset(
                                y = Dp(offsetY / density.density),
                                x = 60.dp
                            )
                            .background(Color.Red)
                            .zIndex(2f) // trên tất cả
                    )
                }

                // Divider vẽ sau cùng, dưới cùng
                HorizontalDivider(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 60.dp)
                        .zIndex(0f),
                    thickness = 1.dp,
                    color = Color.Black
                )
            }
        }
    }
}
