package com.example.suppy.util

/**
 * Class to be used in the same way as [SingleLiveEvent]
 * but has no content
 * @author https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 */
class VoidEvent{
    private var handled = false
    fun handled(): Boolean = if (handled) {
        true
    } else {
        handled = true
        false
    }
}