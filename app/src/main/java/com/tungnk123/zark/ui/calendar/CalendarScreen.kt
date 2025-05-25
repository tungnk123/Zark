package com.tungnk123.zark.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.tungnk123.top_sheet.data.rememberTopSheetState
import com.tungnk123.zark.data.dto.calendar.CalendarDto
import com.tungnk123.zark.ui.calendar.composables.CalendarTopBar
import com.tungnk123.zark.ui.calendar.composables.CalendarType
import com.tungnk123.zark.ui.calendar.composables.ChangeCalendarTypeTopSheet
import com.tungnk123.zark.ui.calendar.composables.MonthCalendarView
import com.tungnk123.zark.ui.calendar.composables.SingleDayView
import com.tungnk123.zark.ui.calendar.composables.ThreeDayView
import com.tungnk123.zark.ui.calendar.composables.TimeAgendaView
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun CalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
//    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    var selectedType by remember { mutableStateOf(CalendarType.SCHEDULE) }
    val topSheetState = rememberTopSheetState()
    val scope = rememberCoroutineScope()

//    val events by calendarViewModel.events.collectAsState()

    val events = listOf(
        CalendarDto(
            id = "1",
            title = "Team Meeting",
            startTime = LocalDateTime.now()
                .withHour(14)
                .withMinute(30),
            endTime = LocalDateTime.now()
                .withHour(15)
                .withMinute(30),
        ),
        CalendarDto(
            id = "2",
            title = "Code Review",
            startTime = LocalDateTime.now()
                .withHour(9)
                .withMinute(0),
            endTime = LocalDateTime.now()
                .withHour(10)
                .withMinute(0),
        ),
        CalendarDto(
            id = "3",
            title = "Code Review 3",
            startTime = LocalDateTime.now()
                .withHour(22)
                .withMinute(0),
            endTime = LocalDateTime.now()
                .withHour(22)
                .withMinute(30),
        )
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                currentDateTime = LocalDateTime.now(),
                onChangeDayClick = {},
                onSearchClick = {
                },
                onChangeCalendarTypeClick = {
                    scope.launch {
                        topSheetState.expand()
                    }
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column {
            when (selectedType) {
                CalendarType.SCHEDULE -> TimeAgendaView(
                    events = events,
                    modifier = Modifier.padding(padding)
                )

                CalendarType.DAY -> SingleDayView(
                    events = events,
                    modifier = Modifier.padding(padding)
                )
                CalendarType.THREE_DAY -> ThreeDayView(
                    events = events,
                    modifier = Modifier.padding(padding)
                )

                CalendarType.WEEK -> ThreeDayView(
                    events = events,
                    modifier = Modifier.padding(padding)
                )
                CalendarType.MONTH -> MonthCalendarView(modifier = Modifier.padding(padding))
            }
        }
    }

    ChangeCalendarTypeTopSheet(
        topSheetState = topSheetState,
        selectedType = selectedType,
        onTypeSelected = { selectedType = it },
        onDismissRequest = {
            scope.launch {
                topSheetState.collapse()
            }
        },
    )
}
