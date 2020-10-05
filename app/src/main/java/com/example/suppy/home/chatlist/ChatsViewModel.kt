package com.example.suppy.home.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.chat.EntityChat
import com.example.repository.ChatRepo
import com.example.repository.webservicemodule.Server
import com.example.suppy.util.VoidEvent
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.util.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * NOTE use [AndroidViewModel] only if your view model requires context
 */
class ChatsViewModel : ViewModel() {
    lateinit var bundle: String

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

    fun getAllChatLocalData(): LiveData<List<EntityChat>> {
        Timber.d("Returning live data from VM (calling repo)")
        return ChatRepo().chats()
    }

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
        Timber.d("insert this: $s")
        viewModelScope.launch (Dispatchers.IO){
            ChatRepo().insert(
                s
            )
        }
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