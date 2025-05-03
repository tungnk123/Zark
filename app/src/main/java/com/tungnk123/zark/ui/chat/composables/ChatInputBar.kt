package com.tungnk123.zark.ui.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_1B56FD
import com.tungnk123.zark.ui.theme.c_848484
import com.tungnk123.zark.ui.theme.c_BABFC4
import com.tungnk123.zark.ui.theme.c_F6F6F6

@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    placeholder: String,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                WindowInsets.ime.exclude(WindowInsets.navigationBars)
            )
            .background(c_F6F6F6)
            .padding(8.dp),
    ) {
        TextField(
            value = message,
            onValueChange = onMessageChange,
            trailingIcon = {
                if (message.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onMessageChange("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.msg_clear_search)
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = c_848484,
                        fontSize = 16.sp
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = modifier
                .fillMaxWidth(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { /* emoji */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_icon_picker),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* mention */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_acong),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* image */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_image),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* mic */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* text style */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_color_format),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* more */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus_circle),
                    contentDescription = null
                )
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = onSendClick,
                enabled = message.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (message.isEmpty()) c_BABFC4 else c_1B56FD,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatInputBarPreview() {
    val (text, setText) = remember { mutableStateOf("Hello") }

    ChatInputBar(
        message = text,
        onMessageChange = setText,
        placeholder = "Chat something",
        onSendClick = {},
    )
}
