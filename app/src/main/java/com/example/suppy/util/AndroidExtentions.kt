package com.example.suppy.util

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.suppy.experimental.SingleLiveEvent
import com.example.suppy.experimental.VoidEvent
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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
 * Extension function to better observe events that require content
 * @sample viewModel.variable.observeEvent(owner) { myString ->
 *      // your action that may require data
 * }
 * @author https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 */
fun <T> LiveData<out SingleLiveEvent<T>>.observeEvent(owner: LifecycleOwner, onEventUnhandledContent: (T) -> Unit) {
    observe(owner, Observer{
        it?.content()?.let(onEventUnhandledContent)
    })
}

/**
 * Extension function to better observe events that require no content
 * @sample viewModel.variable.observeEvent(owner) {
 *      // your action that requires no data
 * }
 * @author https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 */
fun LiveData<out VoidEvent>.observeEvent(owner: LifecycleOwner, onEventUnhandledContent: () -> Unit) {
    observe(owner, Observer { if (!it.handled()) onEventUnhandledContent() })
}

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

/**
 * @desc Convenience function for fragment bundle delegation
 * @sample var param2: String by argument()
 * @author https://proandroiddev.com/kotlin-delegates-in-android-1ab0a715762d
 * TODO See if this is usable for this project
 */
fun <T: Any> argument(): ReadWriteProperty<Fragment, T> = FragmentArgumentDelegate()

/**
 * This extension function acts as a delegate for storing
 * string values in [SharedPreferences]
 * @sample var myParam by prefs.string()
 * @author https://proandroiddev.com/kotlin-delegates-in-android-1ab0a715762d
 * TODO determine if it will be useful
 */
fun SharedPreferences.string(
    defVal: String = "",
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, String> = object : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>)
            = getString(key(property), defVal)!!
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String)
            = edit().putString(key(property), value).apply()
}

/**
 * This extension function acts as a delegate for storing
 * Integer values in [SharedPreferences]
 * @sample var myParam by prefs.int()
 * @author https://proandroiddev.com/kotlin-delegates-in-android-1ab0a715762d
 * TODO determine if it will be useful
 */
fun SharedPreferences.int(
    defVal: Int = 0,
    key: (KProperty<*>) -> String = KProperty<*>::name
) : ReadWriteProperty<Any, Int> = object : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>)
        = getInt(key(property), defVal)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int)
        = edit().putInt(key(property), value).apply()
}

/**
 * Extension function on TextView to be used as delegate
 * @sample var title by tv_title.text()
 * @author https://proandroiddev.com/kotlin-delegates-in-android-1ab0a715762d
 * TODO determine if it will be useful
 */
fun TextView.text(): ReadWriteProperty<Any, String> = object : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = text.toString()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        text = value
    }
}
