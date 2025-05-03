import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import com.tungnk123.zark.ui.theme.c_848484
import com.tungnk123.zark.ui.theme.c_D3E2FF
import com.tungnk123.zark.ui.theme.c_F4F3F8
import java.time.LocalDateTime

@Composable
fun MessageItem(
    chat: ChatMessageResponse,
    isMe: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
        ) {
            Row(
                horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isMe) c_D3E2FF else c_F4F3F8)
                        .padding(12.dp)
                        .widthIn(max = 260.dp)
                ) {
                    Text(
                        text = chat.message,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                if (isMe) {
                    Icon(
                        imageVector = if (chat.isSeen) Icons.Default.DoneAll else Icons.Default.Done,
                        contentDescription = if (chat.isSeen) "Seen" else "Sent",
                        tint = if (chat.isSeen) Color.Blue else Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        if (chat.isPinned) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
            ) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = "Pinned",
                    tint = c_848484,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Pinned Message",
                    fontSize = 12.sp,
                    color = c_848484
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Message from me (seen & pinned)"
)
@Composable
fun PreviewMessageItemMe() {
    MessageItem(
        chat = ChatMessageResponse(
            chatMessageId = 1,
            conversationId = 100,
            userSendId = 123,
            senderDisplayName = "Me",
            message = "Hey, how are you?",
            type = "text",
            sendDate = LocalDateTime.now(),
            isSeen = true,
            isPinned = true,
            mediaLink = null
        ),
        isMe = true
    )
}

@Preview(
    showBackground = true,
    name = "Message from other"
)
@Composable
fun PreviewMessageItemOther() {
    MessageItem(
        chat = ChatMessageResponse(
            chatMessageId = 2,
            conversationId = 100,
            userSendId = 456,
            senderDisplayName = "Alice",
            message = "I'm good, thanks! 😊",
            type = "text",
            sendDate = LocalDateTime.now(),
            isSeen = false,
            isPinned = false,
            mediaLink = null
        ),
        isMe = false
    )
}
