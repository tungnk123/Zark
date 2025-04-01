package com.tungnk123.zark.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_4A86F7
import com.tungnk123.zark.ui.theme.c_E9EDF1

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    @DrawableRes leadIconResId: Int,
    modifier: Modifier = Modifier,
    @DrawableRes trailingIconResId: Int? = null,
    isPasswordField: Boolean = false,
    hasRedAsteroid: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = buildAnnotatedString {
                append(stringResource(labelResId))
                if (hasRedAsteroid) {
                    withStyle(SpanStyle(color = Color.Red)) {
                        append(stringResource(R.string.msg_asteroid))
                    }
                }
            })
        },
        leadingIcon = {
            Icon(
                painter = painterResource(leadIconResId),
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (trailingIconResId != null) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(trailingIconResId),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = c_4A86F7,
            unfocusedBorderColor = c_E9EDF1
        ),
        singleLine = true,
        visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    CustomTextField(
        value = text,
        onValueChange = { text = it },
        labelResId = R.string.msg_password,
        leadIconResId = R.drawable.ic_launcher_foreground,
        trailingIconResId = R.drawable.ic_launcher_foreground,
        isPasswordField = true,
        modifier = Modifier.padding(16.dp)
    )
}
