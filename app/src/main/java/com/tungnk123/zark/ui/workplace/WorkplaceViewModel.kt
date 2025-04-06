package com.tungnk123.zark.ui.workplace

import androidx.lifecycle.ViewModel
import com.tungnk123.zark.repository.chat.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkplaceViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

}