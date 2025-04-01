package com.tungnk123.zark.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_6A7185

@Composable
fun TermsAndPrivacyText() {
    val fullText = stringResource(R.string.msg_confirm_term_privacy_policy)
    val termsText = stringResource(R.string.msg_term)
    val privacyText = stringResource(R.string.msg_privacy_policy)

    val annotatedText = buildAnnotatedString {
        val termsStart = fullText.indexOf(termsText)
        val privacyStart = fullText.indexOf(privacyText)

        append(fullText)

        if (termsStart >= 0) {
            addStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                ),
                start = termsStart,
                end = termsStart + termsText.length
            )
        }

        if (privacyStart >= 0) {
            addStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                ),
                start = privacyStart,
                end = privacyStart + privacyText.length
            )
        }
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = c_6A7185,
            fontSize = 11.sp
        )
    )
}
