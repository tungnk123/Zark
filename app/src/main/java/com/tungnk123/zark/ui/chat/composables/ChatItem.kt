package com.tungnk123.zark.ui.chat.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Uri
import coil3.compose.AsyncImage
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_4A86F7
import com.tungnk123.zark.ui.theme.c_848484
import java.time.LocalDateTime

@Composable
fun ChatItem(
    name: String,
    logoUrl: Uri? = null,
    lastMessage: String,
    lastChatTime: LocalDateTime,
    isSeen: Boolean,
    onChatItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes defaultLogoResId: Int = R.drawable.ic_logo,
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp).clickable(onClick = onChatItemClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = defaultLogoResId,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Black,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lastMessage,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isSeen) c_848484 else Color.Black,
                    fontSize = 12.sp,
                    fontWeight = if (isSeen) FontWeight.W400 else FontWeight.W600
                )
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column(
            modifier = Modifier.height(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = lastChatTime.hour.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = c_848484,
                    fontSize = 13.sp
                )
            )
            if (!isSeen) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(c_4A86F7)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    ChatItem(
        name = "John Doe",
        lastMessage = "Hello, how are you?",
        lastChatTime = LocalDateTime.now(),
        isSeen = false,
        onChatItemClick = {}
    )
}
