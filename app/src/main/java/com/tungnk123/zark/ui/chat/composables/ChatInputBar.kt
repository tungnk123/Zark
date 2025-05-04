package com.tungnk123.zark.ui.chat.composables

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.common.EmojiSelector
import com.tungnk123.zark.ui.common.FunctionalityNotAvailablePanel
import com.tungnk123.zark.ui.common.NotAvailablePopup
import com.tungnk123.zark.ui.theme.c_1B56FD
import com.tungnk123.zark.ui.theme.c_848484
import com.tungnk123.zark.ui.theme.c_BABFC4
import com.tungnk123.zark.ui.theme.c_F6F6F6

enum class InputSelector {
    NONE, EMOJI, MENTION, IMAGE, MIC, TEXT_STYLE, MORE
}

@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    placeholder: String,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }
    if (currentInputSelector != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboard)
    }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            InputSelectorRow(
                currentSelector = currentInputSelector,
                onSelectorClicked = { selected ->
                    currentInputSelector = selected
                }
            )

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

        SelectorExpanded(
            onCloseRequested = dismissKeyboard,
            onTextAdded = { newText ->
                onMessageChange(message + newText)
            },
            currentSelector = currentInputSelector
        )
    }
}

@Composable
private fun InputSelectorRow(
    currentSelector: InputSelector,
    onSelectorClicked: (InputSelector) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        @Composable
        fun selectorIcon(
            iconRes: Int,
            selector: InputSelector
        ) {
            IconButton(onClick = {
                val toggled = if (currentSelector == selector) InputSelector.NONE else selector
                onSelectorClicked(toggled)
            }) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null
                )
            }
        }

        selectorIcon(R.drawable.ic_icon_picker, InputSelector.EMOJI)
        selectorIcon(R.drawable.ic_acong, InputSelector.MENTION)
        selectorIcon(R.drawable.ic_image, InputSelector.IMAGE)
        selectorIcon(R.drawable.ic_mic, InputSelector.MIC)
        selectorIcon(R.drawable.ic_color_format, InputSelector.TEXT_STYLE)
        selectorIcon(R.drawable.ic_plus_circle, InputSelector.MORE)
    }
}


@Composable
private fun SelectorExpanded(
    currentSelector: InputSelector,
    onCloseRequested: () -> Unit,
    onTextAdded: (String) -> Unit
) {
    if (currentSelector == InputSelector.NONE) return

    val focusRequester = FocusRequester()
    SideEffect {
        if (currentSelector == InputSelector.EMOJI) {
            focusRequester.requestFocus()
        }
    }

    Surface(tonalElevation = 8.dp) {
        when (currentSelector) {
            InputSelector.EMOJI -> EmojiSelector(
                onTextAdded,
                focusRequester
            )

            InputSelector.NONE -> FunctionalityNotAvailablePanel()
            InputSelector.MENTION -> NotAvailablePopup(onCloseRequested)
            InputSelector.IMAGE -> FunctionalityNotAvailablePanel()
            InputSelector.MIC -> FunctionalityNotAvailablePanel()
            InputSelector.TEXT_STYLE -> FunctionalityNotAvailablePanel()
            InputSelector.MORE -> FunctionalityNotAvailablePanel()
        }
    }
}

private fun TextFieldValue.addText(newString: String): TextFieldValue {
    val newText = this.text.replaceRange(
        this.selection.start,
        this.selection.end,
        newString
    )
    val newSelection = TextRange(
        start = newText.length,
        end = newText.length
    )

    return this.copy(
        text = newText,
        selection = newSelection
    )
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
