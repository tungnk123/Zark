package com.tungnk123.zark.ui.search.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Uri
import coil3.compose.AsyncImage
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.ZarkTheme

@Composable
fun SearchConversationItem(
    name: String,
    logoUrl: Uri? = null,
    onChatItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes defaultLogoResId: Int = R.drawable.ic_logo,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onChatItemClick)
            .padding(
                vertical = 12.dp,
            ),
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

        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color.Black,
                fontSize = 14.sp
            ),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            imageVector = Icons.Default.ContactMail,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewSearchConversationItem() {
    ZarkTheme {
        SearchConversationItem(
            name = "Tran Yii",
            logoUrl = null,
            onChatItemClick = {},
        )
    }
}
