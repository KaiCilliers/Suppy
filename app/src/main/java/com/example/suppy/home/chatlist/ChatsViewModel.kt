package com.example.suppy.home.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.chat.DomainChat
import com.example.models.chat.EntityChat
import com.example.models.message.EntityMessage
import com.example.models.message.UpdateMessageReceived
import com.example.repository.ChatRepo
import com.example.repository.MessageRepo
import com.example.repository.webservicemodule.Server
import com.example.suppy.util.VoidEvent
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.util.CoroutineContextSource
import com.example.suppy.util.viewModelIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * NOTE use [AndroidViewModel] only if your view model requires context
 * TODO pass repo reference to chatsViewmodel
 * TODO pass coroutine dispatcher here for testing later down the line
 * EXAMPLE
 * priv val repo: Repo,
 * priv val ioDispatcher: CoroutineDispatcher
 */
class ChatsViewModel(val repo: ChatRepo) : ViewModel() {
    lateinit var bundle: DomainChat
    lateinit var chatMan: ChatManager
    lateinit var chat: Chat

    /**
     * Simply to take note of the viewmodel's
     * lifecycle awareness aspects
     * TODO temp
     */
    init {
        Timber.d("ChatsViewModel created!")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.d("ChatsViewModel destroyed!")
    }

    /**
     * Used to store randomly generated chat records
     * to be used to remove those records again on
     * button click to test recyclerview reaction
     * to database record deletion
     * TODO temp
     */
    private var addedRecords: ArrayList<EntityChat> = arrayListOf()

    /**
     * Standard UI navigation code
     * TODO replace the get with a delegation if possible
     */
    private val _navigateToChatMessages = MutableLiveData<VoidEvent>()
    val navigateToChatMessages: LiveData<VoidEvent>
        get() = _navigateToChatMessages
    fun navigate() {
        _navigateToChatMessages.value = VoidEvent()
        Timber.d("Chat clicked from VM: $bundle")
    }

    /**
     * Start server connection
     */
    fun startServerConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            Server.instance()
        }
    }

    /**
     * Delete a chat by name which is
     * obtained from the list item
     * in the recyclerview
     */
    fun deleteByName(name: String) {
        viewModelScope.launch {
            // TODO also provide a variable here...or maybe just use Dispatchers.IO?
            withContext(CoroutineContextSource().io) {
                repo.deleteRow(name)
            }
        }
    }

    /**
     * Return all chats from database wrapped
     * in LiveData
     * TODO temp
     */
    fun getAllChatLocalData(): LiveData<List<EntityChat>> {
        Timber.d("Returning chat live data from VM (calling repo)")
        return repo.chats()
    }

    /**
     * Return all messages from database wrapped
     * in LiveData
     * TODO temp
     */
    fun getLatestMessaageLocalData(): LiveData<EntityMessage> {
        Timber.d("Returning latest message live data from VM (calling repo)")
        return MessageRepo().latestMessage()
    }

    /**
     * Update chat item's description when a new
     * message arrives that belongs to it
     */
    fun updateChatWithNewMessage(message: EntityMessage) {
        Timber.d("OK, i am going to update the chat item this message: ${message.body}...")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                /**
                 * Updating the message's received value here to allow
                 * the UI to display it, because there is a bug that
                 * displays the latest message on each launch as if
                 * it is a new message, thus a received field was added
                 * and a check put in place, hence why the received value
                 * is set to false when the [ConnectionListener] receives it
                 */
                Timber.d("Updating message received field: ${message.body} and ${message.fromName}")
                val messageUpdate = UpdateMessageReceived(message.id, true)
                MessageRepo().updateReceivedValue(messageUpdate)
                /**
                 * Fetch chat id using the sender's name which is not ideal but
                 * it'll do for now. Then the id is used to update the chat with
                 * a partial entity object which notifies the view model of data that
                 * changed and in turn the recyclerview's data gets refreshed with
                 * the latest data
                 */
                val chatId = repo.getChatIdOf(message.fromName)
                Timber.d("Id of chat to be updated: $chatId with message ${message.body}")
                repo.updateDescriptionOf(chatId, message.body)
            }
        }
    }

    /**
     * Display the amount of randomly generated chats
     * that is suppose to exist in the database
     * TODO temp
     */
    private fun randomRecordsCount() {
        Timber.d("RANDOM RECORDS COUNT: ${addedRecords.size}")
    }

    /**
     * Fetch all chats from databse to be
     * displayed to confirm autogenerated
     * ID field
     * TODO temp
     */
    fun localRecords() {
        randomRecordsCount()
        Timber.d("Viewmodel wants to fetch all the chats from local storage...")
        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.justChats()
            Timber.d("Local chats from database:")
            data.forEach {
                Timber.d("[id=${it.id}, name=${it.chatName}]")
            }
        }
    }

    /**
     * Delete a record in local storage from an
     * arraylist of randomly generated records
     * that have been entered into local storage
     * TODO temp
     */
    fun deleteRecord() {
        val random = Random().nextInt(addedRecords.size)
        Timber.d("Random int: $random")
        val toBeRemoved = addedRecords.get(random)
        addedRecords.remove(toBeRemoved)
        randomRecordsCount()
        // TODO replace rest of viewmodelscope.launch with this extension function
        viewModelIO {
            repo.deleteRow(toBeRemoved.chatName)
        }
    }

    /**
     * Create a chat entity to be added to local
     * database to confirm recyclerview data is
     * updated when data changes
     * TODO temp
     */
    fun insertRandomChat() {
        val s = EntityChat(
            chatName = "Person ${Random().nextInt(9999)}",
            lastActivity = "las",
            mute = false,
            description = "def desc",
            creator = "cre",
            createdAt = "cred at",
            subType = "subty",
            bareJid = "jidd",
            approved = true,
            subPending = false,
            commonGroups = "no groups yo"
        )
        addedRecords.add(s) // saved to reference when wanting to delete a list item
        randomRecordsCount()
        Timber.d("insert this: $s")
        viewModelScope.launch (Dispatchers.IO){
            repo.insert(
                s
            )
        }
    }

    /**
     * Build and send a message stanza
     * TODO temp
     */
    fun sendMsgStanza(content: String, to: String) {
//        val msgStanza = Message(
//            JidCreate.bareFrom("${to}@jabber-hosting.de"),
//            "$content"
//        )
//        msgStanza.type = Message.Type.chat
//        Server.instance().sendStanza(msgStanza)
//        if(!::chat.isInitialized) {
//            if(!::chatMan.isInitialized) {
//                chatMan = ChatManager.getInstanceFor(Server.instance())
//                chatMan.addIncomingListener(ConnectionListener())
//            }
//            chat = chatMan.chatWith(JidCreate.entityBareFrom("${to}@jabber-hosting.de"))
//        }
//        chat.send("$content")
    }

    /**
     * Dummy data
     *
     * TODO Replace with real data :)
     */
    val data = arrayListOf(
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        ),
        SomeDataModel(
            "Chat with ${Random().nextInt(
                9999
            )}", "Description ${Random().nextInt(9999)}"
        )
    )
}