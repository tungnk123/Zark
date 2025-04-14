package com.tungnk123.zark.ui.calendar

import androidx.lifecycle.ViewModel
import com.tungnk123.zark.repository.message.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

}