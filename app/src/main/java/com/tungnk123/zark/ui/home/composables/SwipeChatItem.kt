package com.tungnk123.zark.ui.home.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Uri
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.ZarkTheme
import com.tungnk123.zark.ui.theme.c_4CB125
import com.tungnk123.zark.ui.theme.c_6A7185
import com.tungnk123.zark.ui.theme.c_D94841
import com.tungnk123.zark.ui.theme.c_F2A84C
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun SwipeChatItem(
    name: String,
    logoUrl: Uri? = null,
    lastMessage: String,
    lastChatTime: LocalDateTime,
    isSeen: Boolean,
    onChatItemClick: () -> Unit,
    onNotify: () -> Unit,
    onDelete: () -> Unit,
    onArchive: () -> Unit,
    onMarkUnread: () -> Unit,
    onPin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val maxSwipeLeft = 700f
    val maxSwipeRight = 500f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val newOffset = swipeOffset.value + dragAmount
                            val clampedOffset = newOffset.coerceIn(
                                -maxSwipeLeft,
                                maxSwipeRight
                            )
                            swipeOffset.snapTo(clampedOffset)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            val endOffset = swipeOffset.value
                            when {
                                endOffset < -100f -> swipeOffset.animateTo(-maxSwipeLeft)
                                endOffset > 100f -> swipeOffset.animateTo(maxSwipeRight)
                                else -> swipeOffset.animateTo(0f)
                            }
                        }
                    })
            }) {
        if (swipeOffset.value > 0f) {
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.Start
            ) {
                ActionButton(
                    text = stringResource(R.string.msg_unread),
                    iconResId = R.drawable.ic_unread,
                    color = c_6A7185,
                    onClick = onMarkUnread
                )
                ActionButton(
                    text = stringResource(R.string.msg_pin),
                    iconResId = R.drawable.ic_pin,
                    color = c_4CB125,
                    onClick = onPin
                )
            }
        }

        if (swipeOffset.value < 0f) {
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(
                    text = stringResource(R.string.msg_mute),
                    iconResId = R.drawable.ic_notify,
                     color = c_F2A84C,
                    onClick = onNotify
                )
                ActionButton(
                    text = stringResource(R.string.msg_delete),
                    iconResId = R.drawable.ic_delete,
                    color = c_D94841,
                    onClick = onDelete
                )
                ActionButton(
                    text = stringResource(R.string.msg_archive),
                    R.drawable.ic_archive,
                    color = c_6A7185,
                    onClick = onArchive
                )
            }
        }
        ChatItem(
            name = name,
            lastMessage = lastMessage,
            logoUrl = logoUrl,
            lastChatTime = lastChatTime,
            isSeen = isSeen,
            onChatItemClick = {
                scope.launch {
                    swipeOffset.animateTo(0F)
                }
                onChatItemClick.invoke()
            },
            modifier = Modifier
                .offset {
                    IntOffset(
                        swipeOffset.value.toInt(),
                        0
                    )
                }
                .fillMaxWidth()
                .background(Color.White))
    }
}


@Composable
fun ActionButton(
    text: String,
    @DrawableRes iconResId: Int,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color)
            .aspectRatio(1.0F)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontSize = 10.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewActionBar() {
    ZarkTheme {
        ActionButton(
            "Thông báo",
            R.drawable.ic_chat,
            Color.Gray,
            onClick = {})
    }
}