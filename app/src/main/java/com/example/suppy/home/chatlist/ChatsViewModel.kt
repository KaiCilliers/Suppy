package com.example.suppy.home.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suppy.experimental.VoidEvent
import com.example.suppy.move_out.SomeDataModel
import timber.log.Timber
import java.util.*

/**
 * NOTE use [AndroidViewModel] only if your view model requires context
 */
class ChatsViewModel : ViewModel() {
    lateinit var bundle: String

    /**
     * Standard UI navigation code
     */
    private val _navigateToChatMessages = MutableLiveData<VoidEvent>()
    val navigateToChatMessages: LiveData<VoidEvent>
        get() = _navigateToChatMessages
    fun navigate() {
        _navigateToChatMessages.value = VoidEvent()
        Timber.d("Chat clicked from VM: $bundle")
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