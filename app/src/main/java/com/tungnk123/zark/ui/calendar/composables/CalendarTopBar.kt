package com.tungnk123.zark.ui.calendar.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.ZarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopBar(
    currentDay: String,
    onChangeDayClick: () -> Unit,
    onSearchClick: () -> Unit,
    onChangeCalendarTypeClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes chatIcon: Int = R.drawable.ic_logo,
) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        title = {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = chatIcon,
                        contentDescription = null,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        onClick = onChangeDayClick
                    )
                ) {
                    Text(
                        text = currentDay,
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        null
                    )
                }
            }

        },
        actions = {
            IconButton(onClick = onChangeCalendarTypeClick) {
                Icon(
                    Icons.Default.EditCalendar,
                    contentDescription = stringResource(R.string.msg_search_icon),
                    tint = Color.Black
                )
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.msg_search_icon),
                    tint = Color.Black
                )
            }
        },
        modifier = modifier.background(Color.White),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarTopBarPreview() {
    ZarkTheme {
        CalendarTopBar(
            currentDay = "thg 5, 2025",
            onChangeDayClick = {},
            onSearchClick = {},
            onChangeCalendarTypeClick = {}
        )
    }
}
