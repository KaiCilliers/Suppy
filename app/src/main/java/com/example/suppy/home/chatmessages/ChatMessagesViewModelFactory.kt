package com.example.suppy.home.chatmessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repository.MessageRepo
import java.lang.IllegalArgumentException

/**
 * ViewModel Factory for ChatMessagesViewModel
 * TODO an attempt was made to create a generic viewmodel factory, but class data is lost thus T::class.java is not possible
 * TODO solution is make type reified, but that can only be done to functions (only inline I think...)
 */
class ChatMessagesViewModelFactory(private val messageRepo: MessageRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatMessagesViewModel::class.java)) {
            return ChatMessagesViewModel(messageRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - Expected ChatsViewModel and got $modelClass")
    }
}