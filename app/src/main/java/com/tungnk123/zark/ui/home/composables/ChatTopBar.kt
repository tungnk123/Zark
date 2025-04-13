package com.tungnk123.zark.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.ZarkTheme
import com.tungnk123.zark.ui.theme.c_4A86F7

@Composable
fun ChatTopBar(
    onAppLogoClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onAppLogoClick) {
            Text(
                text = stringResource(R.string.msg_zark),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = c_4A86F7,
                    fontSize = 22.sp
                )
            )
        }

        IconButton(onClick = onEditClick) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatTopBar() {
    ZarkTheme {
        ChatTopBar(
            onEditClick = {},
            onAppLogoClick = {}
        )
    }
}