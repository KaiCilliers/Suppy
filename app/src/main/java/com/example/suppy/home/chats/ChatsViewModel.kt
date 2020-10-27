package com.example.suppy.home.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.models.chat.DomainChat
import com.example.models.chat.EntityChat
import com.example.models.message.EntityMessage
import com.example.models.message.UpdatedReceived
import com.example.repository.impl.ChatRepo
import com.example.repository.impl.MessageRepo
import com.example.suppy.util.VoidEvent
import com.example.suppy.util.viewModelIO
import timber.log.Timber

/**
 * NOTE use [AndroidViewModel] only if your view model requires context
 * TODO pass coroutine dispatcher here for testing later down the line
 */
class ChatsViewModel(val repo: ChatRepo, val msgRepo: MessageRepo) : ViewModel() {
    lateinit var bundle: DomainChat
    /**
     * Return all messages that have not been received yet wrapped
     * in LiveData
     */
    fun unReceivedMessagesCounter(): LiveData<List<EntityMessage>> {
        Timber.v("Fetching all unreceived messages wrapped in LiveData...")
        return msgRepo.getAllUnreceivedLiveData()
    }
    /**
     * Standard UI navigation code
     * TODO replace the get with a delegation if possible
     */
    private val _navigateToChatMessages = MutableLiveData<VoidEvent>()
    val navigateToChatMessages: LiveData<VoidEvent>
        get() = _navigateToChatMessages
    fun navigate() {
        _navigateToChatMessages.value = VoidEvent()
    }
    /**
     * Fetch all messages that have not been received
     * Primarily used for debugging
     * TODO temp
     */
    fun allUnReceived(): List<EntityMessage> {
        Timber.v("Fetching all unreceived messages...")
        return msgRepo.getAllUnreceived()
    }
    /**
     * Delete a chat by name which is
     * obtained from the list item
     * in the recyclerview
     */
    fun deleteByName(name: String) {
        viewModelIO {
            Timber.v("Call made to delete chat \"$name\"")
            repo.deleteRow(name)
        }
    }
    /**
     * Return all chats from database wrapped
     * in LiveData
     * TODO temp
     */
    fun getAllChatLocalData(): LiveData<List<EntityChat>> {
        Timber.v("Call made to fetch all chats wrapped in live data...")
        return repo.chats()
    }
    /**
     * Return all messages from database wrapped
     * in LiveData
     * TODO temp
     */
    fun getLatestMessaageLocalData(): LiveData<EntityMessage> {
        Timber.v("Call made to get latest message received wrapped in live data...")
        return msgRepo.latestMessage()
    }
    /**
     * Update chat item's description when a new
     * message arrives that belongs to it
     */
    fun updateChatWithNewMessage(message: EntityMessage) {
        Timber.v("Call made to update chat item description with this message: \"${message.body}\"...")
        viewModelIO {
            /**
             * Updating the message's received value here to allow
             * the UI to display it, because there is a bug that
             * displays the latest message on each launch as if
             * it is a new message, thus a received field was added
             * and a check put in place, hence why the received value
             * is set to false when the [ConnectionListener] receives it
             */
            Timber.v("Updating message received field: ${message.body} and ${message.fromName}")
            val messageUpdate = UpdatedReceived(message.id, true, message.counter)
            msgRepo.updateReceivedValue(messageUpdate)
            /**
             * Fetch chat id using the sender's name which is not ideal but
             * it'll do for now. Then the id is used to update the chat with
             * a partial entity object which notifies the view model of data that
             * changed and in turn the recyclerview's data gets refreshed with
             * the latest data
             */
            val chatId = repo.getChatIdOf(message.fromName)
            Timber.v("Id of chat to be updated: $chatId with message ${message.body}")
            repo.updateDescriptionOf(chatId, message.body)
        }
    }
    /**
     * Update all chats with their new message unreceived counter
     */
    fun updateChatsUnreceivedCounter(data: List<EntityMessage>) {
        Timber.v("Going to update the chats with their unreceived message counter...")
        viewModelIO {
            val chats = repo.chats.invoke()
            chats.forEach {chat ->
                val counter = data.count { it.fromName == chat.chatName }
                repo.updateUnReadOfChat(chat.id, counter)
            }
        }
    }
}
