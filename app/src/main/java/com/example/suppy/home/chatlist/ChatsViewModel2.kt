package com.example.suppy.home.chatlist

import androidx.lifecycle.*
import com.example.suppy.experimental.SingleLiveEvent

/**
 * Example of a viewmodel that can handle events
 * with LiveData
 * @sample viewModel.navToMars.observe(this, Observer {
 *      it.content()?.let {
 *          // your event action
 *      }
 * })
 */
class ChatsViewModel2 : ViewModel() {
    private val _navToMars = MutableLiveData<SingleLiveEvent<String>>()
    val navToMars : LiveData<SingleLiveEvent<String>>
        get() = _navToMars

    /**
     * Trigger the event by setting a new [SingleLiveEvent] as a new value
     */
    fun navigate(itemId: String) {
        _navToMars.value = SingleLiveEvent(itemId)
    }
}



fun <T> LiveData<out SingleLiveEvent<T>>.observeEvent(owner: LifecycleOwner, onEventUnhandledContent: (T) -> Unit) {
    observe(owner, Observer{
        it?.content()?.let(onEventUnhandledContent)
    })
}