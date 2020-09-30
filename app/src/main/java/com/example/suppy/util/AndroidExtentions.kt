package com.example.suppy.util

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.Serializable

/**
 * @desc Subscribe to [LiveData]
 */
inline fun LiveData<Boolean>.subscribeToNavigation(
    owner: LifecycleOwner,
    crossinline actionsBeforeNavigation: () -> Unit,
    crossinline navigation: () -> Unit,
    crossinline resetBool: () -> Unit
) = observe(owner, Observer {navigate ->
    if(navigate) {
        actionsBeforeNavigation()
        navigation()
        resetBool()
    }
})

/**
 * @desc Shorter way of adding bundle data
 */
inline fun <T> Bundle.put(key: String, value: T) {
    when(value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}