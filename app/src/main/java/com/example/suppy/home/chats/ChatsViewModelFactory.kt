package com.example.suppy.home.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repository.ChatRepo
import com.example.repository.MessageRepo
import java.lang.IllegalArgumentException

/**
 * The need for a ViewModelFactory comes from the ViewModelProvider
 * being unable to instantiate a ViewModel that has parameters.
 * With a ViewModelFactory you are able to do just that, and with
 * most ViewModels you need at least a Repository argument passed
 * to it at its instantiation.
 */
class ChatsViewModelFactory(
    private val chatRepository: ChatRepo,
    private val messageRepository: MessageRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            return ChatsViewModel(chatRepository, messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - Expected ChatsViewModel and got $modelClass")
    }
}