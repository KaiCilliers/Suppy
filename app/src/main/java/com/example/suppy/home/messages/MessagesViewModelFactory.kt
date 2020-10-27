package com.example.suppy.home.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repository.impl.MessageRepo
import java.lang.IllegalArgumentException

/**
 * ViewModel Factory for ChatMessagesViewModel
 * TODO an attempt was made to create a generic viewmodel factory, but class data is lost thus T::class.java is not possible
 * TODO solution is make type reified, but that can only be done to functions (only inline I think...)
 */
class MessagesViewModelFactory(private val messageRepo: MessageRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)) {
            return MessagesViewModel(messageRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - Expected ChatsViewModel and got $modelClass")
    }
}