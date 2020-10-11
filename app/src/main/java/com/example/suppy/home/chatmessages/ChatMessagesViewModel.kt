package com.example.suppy.home.chatmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.models.message.EntityMessage
import com.example.repository.MessageRepo
import com.example.suppy.util.VoidEvent
import com.example.suppy.util.viewModelIO
import timber.log.Timber
import kotlin.random.Random

class ChatMessagesViewModel(val repo: MessageRepo) : ViewModel() {
    /**
     * Simply to take note of the viewmodel's
     * lifecycle awareness aspects
     * TODO temp
     */
    init {
        Timber.d("ChatMessagesViewModel created!")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.d("ChatMessagesViewModel destroyed!")
    }

    /**
     * Update all messages from a specific chat to true
     */
    private fun updateAllFromChatAsReceived(chatName: String) {
        Timber.d("Going to update all messages from \"$chatName\" as received" +
                " because the user is on the screen with all the messages and thus it" +
                " can be set as received")
        viewModelIO {
            repo.updateAllMessagesFromChatReceived(chatName)
        }
    }

    /**
     * Returns all messages from a specific chat wrapped in LiveData
     */
    fun getAllMessagesFromChatLocalData(chatName: String): LiveData<List<EntityMessage>> {
        Timber.d("Returning message live data from \"$chatName\" from viewmodel")
        /**
         * Updating the entire chat's messages to have a true value for received attribute
         * TODO records that are fetched are not the latest data as the fetch does not wait for the update to finish
         */
        Timber.d("Update all messages from $chatName to with received set to true")
        updateAllFromChatAsReceived(chatName)
        return repo.allMessagesFrom(chatName)
    }
    /**
     * Basic navigation to Chats screen
     */
    private val _navigateToChats = MutableLiveData<VoidEvent>()
    val navigateToChats: LiveData<VoidEvent>
        get() = _navigateToChats
    fun navigate() {
        _navigateToChats.value = VoidEvent()
    }
}