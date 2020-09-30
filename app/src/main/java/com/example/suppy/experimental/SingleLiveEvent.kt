package com.example.suppy.experimental

/**
 * Class to properly handle events with [LiveData]
 * Events like SnackBar, Navigation, Dialog trigger
 * are all data that needs to be consumed only once
 * Events are thus treated as a state. This class
 * is used as a wrapper for data that is exposed
 * via LiveData that represents an event.
 * @author https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class SingleLiveEvent<out T>(private val content: T){
    var handled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again
     */
    fun content(): T? = if (handled) {
        null
    } else {
        handled = true
        content
    }

    /**
     * Returns content, even if it's been handled
     */
    fun peek(): T = content
}