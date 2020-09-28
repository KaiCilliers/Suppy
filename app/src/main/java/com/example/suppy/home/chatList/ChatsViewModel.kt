package com.example.suppy.home.chatList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suppy.move_out.SomeDataModel
import java.util.*

/**
 * NOTE user [AndroidViewModel] only if your view model requires context
 */
class ChatsViewModel : ViewModel() {
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
    private val _navigateToChatMessages = MutableLiveData<Boolean>()
    val navigateToChatMessages: LiveData<Boolean>
        get() = _navigateToChatMessages
    fun navigate() {
        _navigateToChatMessages.value = true
    }
    fun onNavigatedToChatMessages() {
        _navigateToChatMessages.value = false
    }
}