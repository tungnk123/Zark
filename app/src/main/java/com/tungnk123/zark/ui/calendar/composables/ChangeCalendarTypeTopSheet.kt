package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.ViewDay
import androidx.compose.material.icons.filled.ViewWeek
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tungnk123.top_sheet.data.TopSheetState
import com.tungnk123.top_sheet.ui.TopSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCalendarTypeTopSheet(
    topSheetState: TopSheetState,
    selectedType: CalendarType,
    onTypeSelected: (CalendarType) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopSheet(
        modifier = modifier.wrapContentSize(),
        sheetHeight = 200.dp,
        topSheetState = topSheetState,
        onDismissRequest = onDismissRequest,
        windowInsets = WindowInsets(
            0,
            0,
            0,
            0
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    "Chọn loại lịch",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    CalendarType.entries.forEach { type ->
                        val isSelected = type == selectedType
                        val backgroundColor =
                            if (isSelected) Color(0xFFE0F7FA) else Color.Transparent
                        val iconColor = if (isSelected) Color.Blue else Color.Gray

                        Column(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    backgroundColor,
                                )
                                .clickable { onTypeSelected(type) }
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = type.icon,
                                contentDescription = type.displayName,
                                tint = iconColor,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = type.displayName,
                                color = if (isSelected) Color.Blue else Color.Black,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        },
    )
}

enum class CalendarType(
    val displayName: String,
    val icon: ImageVector,
) {
    SCHEDULE(
        "Lịch biểu",
        Icons.Default.Schedule
    ),
    DAY(
        "Ngày",
        Icons.Default.Today
    ),
    THREE_DAY(
        "3 ngày",
        Icons.Default.ViewDay
    ),
    WEEK(
        "Tuần",
        Icons.Default.ViewWeek
    ),
    MONTH(
        "Tháng",
        Icons.Default.DateRange
    )
}

