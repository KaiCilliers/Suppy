package com.example.suppy.home.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suppy.move_out.SomeDataModel
import timber.log.Timber
import java.util.*

/**
 * NOTE use [AndroidViewModel] only if your view model requires context
 */
class ChatsViewModel : ViewModel() {
    // Temporary way of passing data between recyclerview and fragment
    lateinit var bundle: Any

    /**
     * Standard UI navigation code
     */
    private val _navigateToChatMessages = MutableLiveData<Boolean>()
    val navigateToChatMessages: LiveData<Boolean>
        get() = _navigateToChatMessages
    fun navigate() {
        _navigateToChatMessages.value = true
        Timber.d("I navigate with bundle mayhap? $bundle")
    }
    fun onNavigatedToChatMessages() {
        _navigateToChatMessages.value = false
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