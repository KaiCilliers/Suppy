package com.example.suppy.experimental

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<SingleLiveEvent<T>> {
    override fun onChanged(event: SingleLiveEvent<T>?) {
        event?.content()?.let(onEventUnhandledContent)
    }
}