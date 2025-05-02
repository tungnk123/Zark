package com.tungnk123.zark.ui.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.data.dto.message.ChatMessageResponse

@Composable
fun MessageItem(
    chat: ChatMessageResponse,
    isMe: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isMe) Color(0xFFDCF8C6) else Color(0xFFFFFFFF))
                .padding(12.dp)
                .widthIn(max = 260.dp)
        ) {
            Text(text = chat.message, color = Color.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = chat.sendDate.toString(), color = Color.Gray, fontSize = 10.sp)
        }
    }
}