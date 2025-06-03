package com.tungnk123.zark.ui.event

import android.app.TimePickerDialog
import android.util.Log
import android.widget.TimePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.utils.extensions.printLog
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navController: NavController,
    eventRequestJson: String? = null,
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val eventRequest = remember(eventRequestJson) {
        eventRequestJson?.let { json ->
            try {
                val decodedJson = URLDecoder.decode(
                    json,
                    StandardCharsets.UTF_8.toString()
                )
                Json.decodeFromString<CreateEventRequest>(decodedJson)
                    .also {
                        Log.d(
                            "AddEventScreen",
                            "Parsed CreateEventRequest: $it"
                        )
                    }
            }
            catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    LaunchedEffect(Unit) {
        "Event request json: $eventRequestJson".printLog("AddEventScreen")
        "Event request: $eventRequest".printLog("AddEventScreen")
    }

    var title by remember { mutableStateOf(eventRequest?.title ?: "") }
    var description by remember { mutableStateOf(eventRequest?.description ?: "") }
    var attendees by remember {
        mutableStateOf(
            eventRequest?.participants?.joinToString(",") ?: ""
        )
    }

    var startDateTime by remember {
        mutableStateOf(
            eventRequest?.startTime ?: LocalDateTime.now()
        )
    }
    var endDateTime by remember {
        mutableStateOf(
            eventRequest?.endTime ?: startDateTime.plusHours(1)
        )
    }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = startDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = endDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    val formatter = remember {
        DateTimeFormatter.ofPattern(
            "MMM dd, yyyy - HH:mm",
            Locale.getDefault()
        )
    }

    val timeFormatter = remember {
        DateTimeFormatter.ofPattern(
            "HH:mm",
            Locale.getDefault()
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Event") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                windowInsets = WindowInsets(0),
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    eventViewModel.createEvent(
                        title = title,
                        description = description,
                        startTime = startDateTime,
                        endTime = endDateTime,
                        participants = emptyList()
                    )
                    navController.popBackStack()
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save Event")
            }
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Event Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = attendees,
                onValueChange = { attendees = it },
                label = { Text("Guests (comma-separated emails)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., john@example.com, alice@example.com") }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Date & Time",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = { showStartDatePicker = true }) {
                        Text("Start: ${startDateTime.format(formatter)}")
                    }

                    Button(onClick = { showEndDatePicker = true }) {
                        Text("End: ${endDateTime.format(formatter)}")
                    }

                    val durationHours = Duration.between(
                        startDateTime,
                        endDateTime
                    )
                        .toMinutes()
                        .toFloat() / 60
                    val clampedDuration = max(
                        0f,
                        min(
                            durationHours,
                            24f
                        )
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Duration: %.1f hour(s)".format(clampedDuration))
                        Slider(
                            value = clampedDuration,
                            onValueChange = {},
                            valueRange = 0f..24f,
                            enabled = false
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "From ${startDateTime.format(timeFormatter)}")
                            Text(text = "To ${endDateTime.format(timeFormatter)}")
                        }
                    }
                }
            }
        }
    }

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    startDatePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val time = startDateTime.toLocalTime()
                        showStartDatePicker = false
                        TimePickerDialog(
                            context,
                            { _: TimePicker, hour: Int, minute: Int ->
                                startDateTime = LocalDateTime.of(
                                    date,
                                    LocalTime.of(
                                        hour,
                                        minute
                                    )
                                )
                            },
                            time.hour,
                            time.minute,
                            true
                        ).show()
                    }
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = startDatePickerState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    endDatePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val time = endDateTime.toLocalTime()
                        showEndDatePicker = false
                        TimePickerDialog(
                            context,
                            { _: TimePicker, hour: Int, minute: Int ->
                                endDateTime = LocalDateTime.of(
                                    date,
                                    LocalTime.of(
                                        hour,
                                        minute
                                    )
                                )
                            },
                            time.hour,
                            time.minute,
                            true
                        ).show()
                    }
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = endDatePickerState)
        }
    }
}
