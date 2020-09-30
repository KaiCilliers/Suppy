package com.example.suppy.experimental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Example of a viewmodel that can handle events
 * with LiveData
 * @sample viewModel.navToMars.observe(this, Observer {
 *      it.content()?.let {
 *          // your event action
 *      }
 * })
 */
class NEWChatsViewModel : ViewModel() {
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