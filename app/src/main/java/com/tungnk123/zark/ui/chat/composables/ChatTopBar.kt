package com.tungnk123.zark.ui.chat.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_1B56FD
import com.tungnk123.zark.ui.theme.c_848484

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    navController: NavController,
    chatTitle: String,
    onlineStatus: String,
    onPhoneCallClick: () -> Unit,
    onVideoCallClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes chatIcon: Int = R.drawable.ic_logo,
) {
    TopAppBar(
        windowInsets = androidx.compose.foundation.layout.WindowInsets(0),
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chatTitle,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                            fontSize = 13.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = onlineStatus,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = c_848484,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                }

            }

        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.msg_back),
                    tint = c_1B56FD
                )
            }
        },
        actions = {
            IconButton(onClick = onPhoneCallClick) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = stringResource(R.string.msg_phone_call),
                    tint = c_1B56FD
                )
            }
            IconButton(onClick = onVideoCallClick) {
                Icon(
                    Icons.Default.Videocam,
                    contentDescription = stringResource(R.string.msg_video_call),
                    tint = c_1B56FD
                )
            }
            IconButton(onClick = onInfoClick) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = stringResource(R.string.msg_info),
                    tint = c_1B56FD
                )
            }
        },
        modifier = modifier.background(Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChatTopBar() {
    val navController = rememberNavController()
    ChatTopBar(
        navController = navController,
        chatTitle = "John Doe",
        onlineStatus = "Online",
        onPhoneCallClick = {},
        onVideoCallClick = {},
        onInfoClick = {}
    )
}