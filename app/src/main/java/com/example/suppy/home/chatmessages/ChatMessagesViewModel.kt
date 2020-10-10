package com.example.suppy.home.chatmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suppy.util.VoidEvent
import com.example.suppy.move_out.Message
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.move_out.SomeMessages
import timber.log.Timber
import kotlin.random.Random

class ChatMessagesViewModel : ViewModel() {
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

    val def = SomeMessages(
        SomeDataModel("def", " "),
        arrayListOf(
            Message("nothing...."),
            Message("nothing...2")
        )
    )
    val one = SomeMessages(
        SomeDataModel("1", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val two = SomeMessages(
        SomeDataModel("2", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val three = SomeMessages(
        SomeDataModel("3", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val four = SomeMessages(
        SomeDataModel("4", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val five = SomeMessages(
        SomeDataModel("5", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val six = SomeMessages(
        SomeDataModel("6", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val seven = SomeMessages(
        SomeDataModel("7", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val eight = SomeMessages(
        SomeDataModel("8", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val nine = SomeMessages(
        SomeDataModel("9", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    val zero = SomeMessages(
        SomeDataModel("0", ""),
        arrayListOf(
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}"),
            Message("message ${Random.nextInt(999)}")
        )
    )
    private val _navigateToChats = MutableLiveData<VoidEvent>()
    val navigateToChats: LiveData<VoidEvent>
        get() = _navigateToChats
    fun navigate() {
        _navigateToChats.value = VoidEvent()
    }
}