package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFBBDEFB),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Text(
            text = title,
            color = Color.Black
        )
    }
}
