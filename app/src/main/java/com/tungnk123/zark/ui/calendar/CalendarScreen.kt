package com.tungnk123.zark.ui.calendar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.top_sheet.data.rememberTopSheetState
import com.tungnk123.zark.ui.calendar.composables.CalendarTopBar
import com.tungnk123.zark.ui.calendar.composables.CalendarType
import com.tungnk123.zark.ui.calendar.composables.ChangeCalendarTypeTopSheet
import com.tungnk123.zark.ui.calendar.composables.MonthCalendarView
import com.tungnk123.zark.ui.calendar.composables.SingleDayView
import com.tungnk123.zark.ui.calendar.composables.ThreeDayView
import com.tungnk123.zark.ui.calendar.composables.TimeAgendaView
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem
import com.tungnk123.zark.utils.extensions.navigateToDestination
import com.tungnk123.zark.utils.extensions.navigateToEventDetail
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
) {
    var selectedCalendarType by remember { mutableStateOf(CalendarType.DAY) }
    val topSheetState = rememberTopSheetState()
    val scope = rememberCoroutineScope()
    val events by calendarViewModel.events.collectAsStateWithLifecycle()

    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    val filteredEvents = remember(events, selectedDateTime) {
        events.filter { event ->
            event.startTime.toLocalDate() == selectedDateTime.toLocalDate()
        }
    }

    LaunchedEffect(selectedCalendarType) {
        calendarViewModel.getEvents()
    }

    LaunchedEffect(selectedDateTime) {
        Log.d("CalendarScreen", "Events: $events")
        Log.d("CalendarScreen", "Filtered Events: ${filteredEvents.size}")
        filteredEvents.forEach {
            Log.d("CalendarScreen", "Event: ${it.title} at ${it.startTime}")
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                currentDateTime = selectedDateTime,
                onChangeDayClick = { showDatePicker = true },
                onSearchClick = {
                    navController.navigateToDestination(NavigationBarMetadataItem.Search)
                },
                onChangeCalendarTypeClick = { scope.launch { topSheetState.expand() } },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigateToDestination(NavigationBarMetadataItem.AddEvent)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Event"
                )
            }
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            when (selectedCalendarType) {
                CalendarType.SCHEDULE -> TimeAgendaView(
                    events = filteredEvents,
                    onEventClick = { event ->
                        navController.navigateToEventDetail(event.id)
                    })

                CalendarType.DAY -> SingleDayView(
                    events = filteredEvents,
                    onEventClick = { event ->
                        navController.navigateToEventDetail(event.id)
                    }
                )

                CalendarType.THREE_DAY -> ThreeDayView(events)
                CalendarType.WEEK -> ThreeDayView(events)
                CalendarType.MONTH -> MonthCalendarView()
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
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
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    androidx.compose.material3.Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ChangeCalendarTypeTopSheet(
        topSheetState = topSheetState,
        selectedType = selectedCalendarType,
        onTypeSelected = { selectedCalendarType = it },
        onDismissRequest = { scope.launch { topSheetState.collapse() } }
    )
}