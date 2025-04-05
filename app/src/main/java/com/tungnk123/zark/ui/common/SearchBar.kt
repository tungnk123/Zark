package com.tungnk123.zark.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_848484
import com.tungnk123.zark.ui.theme.c_F2F2F2

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.msg_search_placeholder)
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = c_848484,
                contentDescription = stringResource(R.string.msg_search_icon),
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = onClearQuery,
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
                    fontSize = 13.sp
                )
            )
        },
        shape = RoundedCornerShape(percent = 50),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = c_F2F2F2,
            unfocusedContainerColor = c_F2F2F2,
        ),
        modifier = modifier
            .fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    var query = remember { mutableStateOf("") }

    SearchBar(
        query = query.value,
        onQueryChanged = { query.value = it },
        onClearQuery = { query.value = "" }
    )
}

