package com.tungnk123.zark.ui.search.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.ZarkTheme

@Composable
fun EmptySearchItem(
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.img_no_search_item),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewEmptySearchItem() {
    ZarkTheme {
        EmptySearchItem(
            content = "Please search some keyword to find conversations"
        )
    }
}