package com.example.suppy.home.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.models.message.EntityMessage
import com.example.repository.impl.MessageRepo
import com.example.suppy.util.VoidEvent
import com.example.suppy.util.viewModelIO
import com.example.repository.webservice.Server
import timber.log.Timber

class MessagesViewModel(val repo: MessageRepo) : ViewModel() {
    /**
     * Send a message
     */
    fun send(msg: String) {
        viewModelIO {
            repo.send(msg)
        }
    }
    /**
     * Update all messages from a specific chat to true
     */
    private fun updateAllFromChatAsReceived(chatName: String) {
        Timber.v("""
            Going to update all messages from \"$chatName\" as received
            because the user is on the screen with all the messages and thus it
            can be set as received
        """.trimIndent())
        viewModelIO {
            repo.updateAllMessagesFromChatReceived(chatName)
        }
    }
    /**
     * Returns all messages from a specific chat wrapped in LiveData
     */
    fun getAllMessagesFromChatLocalData(chatName: String): LiveData<List<EntityMessage>> {
        Timber.v("Fetching all messages from \"$chatName\", updating them to be received - then returning...")
        /**
         * Updating the entire chat's messages to have a true value for received attribute
         * TODO records that are fetched are not the latest data as the fetch does not wait for the update to finish
         */
        updateAllFromChatAsReceived(chatName)
        return repo.chatMessages(chatName)

        // TODO
        /**
         * OK so you need to call a new method from repo
         * this method will return all messages from
         * XXX and messages to XXX
         * You can encapsulate the two sets of messages
         * in a new object that take two arrays :)
         * The adapter will have to contain the logic
         * to seperate the messages
         */
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