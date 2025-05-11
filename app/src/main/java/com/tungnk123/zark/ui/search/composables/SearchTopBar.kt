package com.tungnk123.zark.ui.search.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.common.SearchBar
import com.tungnk123.zark.ui.theme.ZarkTheme
import com.tungnk123.zark.ui.theme.c_1B56FD

@Composable
fun SearchTopBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            query = query,
            onQueryChanged = onQueryChanged,
            onClearQuery = onClearQuery,
            modifier = modifier.weight(1f),
        )

        TextButton(onClick = onCancelClick) {
            Text(
                text = stringResource(R.string.msg_cancel),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = c_1B56FD,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchTopBar() {
    ZarkTheme {
        SearchTopBar(
            query = "",
            onQueryChanged = {},
            onClearQuery = {},
            onCancelClick = {},
        )
    }
}