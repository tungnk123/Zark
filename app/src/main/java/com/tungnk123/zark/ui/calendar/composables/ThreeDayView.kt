package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tungnk123.zark.data.dto.calendar.CalendarDto
import java.time.LocalDate

@Composable
fun ThreeDayView(
    events: List<CalendarDto>,
    modifier: Modifier = Modifier,
) {
    val today = LocalDate.now()
    val hourHeight = 60.dp
    val days = List(3) { today.plusDays(it.toLong()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(24) { hour ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(hourHeight)
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .padding(start = 4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(text = "%02d:00".format(hour))
                }

                days.forEach { date ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(horizontal = 4.dp)
                    ) {
                        val eventsThisHour = events.filter {
                            it.startTime.toLocalDate() == date && it.startTime.hour == hour
                        }

                        eventsThisHour.forEach { event ->
                            val minuteOffset = event.startTime.minute
                            val offsetY = with(LocalDensity.current) {
                                (minuteOffset / 60f) * hourHeight.toPx()
                            }

                            Box(
                                modifier = Modifier
                                    .offset(y = Dp(offsetY / LocalDensity.current.density))
                                    .background(
                                        Color(0xFFB3E5FC),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(4.dp)
                            ) {
                                Text(text = event.title)
                            }
                        }
                    }
                }
            }
        }
    }
}