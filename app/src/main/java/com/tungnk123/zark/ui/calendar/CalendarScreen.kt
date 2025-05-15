package com.tungnk123.zark.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.tungnk123.zark.ui.calendar.composables.CalendarTopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val tabItems = listOf(
        "Lịch biểu",
        "Ngày",
        "3 ngày",
        "Tháng"
    )
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                currentDay = LocalDateTime.now()
                    .toString(),
                onChangeDayClick = {},
                onSearchClick = {
                    navController
                },
                onChangeCalendarTypeClick = {}
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column {
            when (selectedTab) {
                0 -> TimeAgendaView(modifier = Modifier.padding(padding))
                1 -> SingleDayView(modifier = Modifier.padding(padding))
                2 -> ThreeDayView(modifier = Modifier.padding(padding))
                3 -> MonthCalendarView(modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun TimeAgendaView(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(24) { hour ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "%02d:00".format(hour),
                    modifier = Modifier.width(60.dp)
                )
                Divider(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SingleDayView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Today: ${LocalDate.now()}")
        TimeAgendaView()
    }
}

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
                TimeAgendaView(modifier = Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthCalendarView(modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    val startMonth = remember {
        YearMonth.now()
            .minusMonths(12)
    }
    val endMonth = remember {
        YearMonth.now()
            .plusMonths(12)
    }
    val currentMonth = remember { YearMonth.now() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    HorizontalCalendar(
        state = state,
        dayContent = { Day(it) }
    )
}

@Composable
fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}