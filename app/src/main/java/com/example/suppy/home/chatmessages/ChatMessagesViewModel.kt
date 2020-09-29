package com.example.suppy.home.chatmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatMessagesViewModel : ViewModel() {
    private val _navigateToChats = MutableLiveData<Boolean>()
    val navigateToChats: LiveData<Boolean>
        get() = _navigateToChats
    fun navigate() {
        _navigateToChats.value = true
    }
    fun onNavigatedToChats() {
        _navigateToChats.value = false
    }
}