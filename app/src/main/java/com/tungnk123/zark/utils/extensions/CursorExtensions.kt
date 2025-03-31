package com.tungnk123.zark.utils.extensions

import android.database.Cursor

private fun Cursor.getStringOrNull(columnIndex: Int): String? {
    return if (!isNull(columnIndex)) getString(columnIndex) else null
}

private fun Cursor.getLongOrNull(columnIndex: Int): Long? {
    return if (!isNull(columnIndex)) getLong(columnIndex) else null
}

private fun Cursor.getIntOrNull(columnIndex: Int): Int? {
    return if (!isNull(columnIndex)) getInt(columnIndex) else null
}