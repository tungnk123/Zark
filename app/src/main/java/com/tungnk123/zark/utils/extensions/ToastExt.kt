package com.tungnk123.zark.utils.extensions

import android.content.Context
import android.widget.Toast


fun Context.showToast(
    value: String, isLong: Boolean = false
) {
    Toast.makeText(
        this, value, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}