package com.tungnk123.zark.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
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
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var selectedType by remember { mutableStateOf(CalendarType.SCHEDULE) }
    val topSheetState = rememberTopSheetState()
    val scope = rememberCoroutineScope()

    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    val events = listOf(
        CalendarDto(
            "1",
            "Team Meeting",
            selectedDateTime.withHour(14)
                .withMinute(30),
            selectedDateTime.withHour(15)
                .withMinute(30)
        ),
        CalendarDto(
            "2",
            "Code Review",
            selectedDateTime.withHour(9),
            selectedDateTime.withHour(10)
        ),
        CalendarDto(
            "3",
            "Code Review 3",
            selectedDateTime.withHour(22),
            selectedDateTime.withHour(22)
                .withMinute(30)
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                currentDateTime = selectedDateTime,
                onChangeDayClick = {
                    showDatePicker = true
                },
                onSearchClick = {

                },
                onChangeCalendarTypeClick = { scope.launch { topSheetState.expand() } },
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column {
            when (selectedType) {
                CalendarType.SCHEDULE -> TimeAgendaView(
                    events,
                    Modifier.padding(padding)
                )

                CalendarType.DAY -> SingleDayView(
                    events,
                    Modifier.padding(padding)
                )

                CalendarType.THREE_DAY -> ThreeDayView(
                    events,
                    Modifier.padding(padding)
                )

                CalendarType.WEEK -> ThreeDayView(
                    events,
                    Modifier.padding(padding)
                )

                CalendarType.MONTH -> MonthCalendarView(Modifier.padding(padding))
            }
        }
    }

    if (showDatePicker) {
        androidx.compose.material3.DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showDatePicker = false
                    val millis = datePickerState.selectedDateMillis
                    millis?.let {
                        val date = java.time.Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        selectedDateTime = date.atStartOfDay()
                    }
                }) {
                    androidx.compose.material3.Text("OK")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showDatePicker = false
                }) {
                    androidx.compose.material3.Text("Cancel")
                }
            }
        ) {
            androidx.compose.material3.DatePicker(state = datePickerState)
        }
    }

    ChangeCalendarTypeTopSheet(
        topSheetState = topSheetState,
        selectedType = selectedType,
        onTypeSelected = { selectedType = it },
        onDismissRequest = { scope.launch { topSheetState.collapse() } }
    )
}