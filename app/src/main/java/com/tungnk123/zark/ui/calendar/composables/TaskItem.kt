package com.tungnk123.zark.ui.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun TaskItem(
    title: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDone: Boolean = false,
    color: Color = Color(0xFFBBDEFB),
) {
    val backgroundColor = if (isDone) Color(0xFFE8F5E8) else color
    val textColor = if (isDone) Color(0xFF4CAF50) else Color.Black
    val textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                4.dp,
                RoundedCornerShape(8.dp)
            )
            .zIndex(1f)
            .background(
                backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onItemClick() }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isDone) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Done",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = title,
                color = textColor,
                textDecoration = textDecoration
            )
        }
    }
}