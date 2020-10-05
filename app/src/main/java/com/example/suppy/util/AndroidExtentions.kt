package com.example.suppy.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @desc Basic subscribe logic to [LiveData]
 */
inline fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, crossinline onDataReceived: (T) -> Unit) =
    observe(owner, Observer { onDataReceived(it) })

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

/**
 * @desc Snackbar creation
 * TODO see if useful
 */
fun snackbar(message: String, rootView: View) = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
fun snackbar(@StringRes message: Int, rootView: View) = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()

/**
 * @desc Programmatically hide keyboard from view
 * TODO see if useful
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * @desc Programmatically adding onclick listeners to views
 */
inline fun View.onClick(crossinline onClick: () -> Unit) = setOnClickListener { onClick() }

/**
 * @desc Easier way of launching coroutines from viewmodel scope
 * TODO see if useful and see how to use it
 */
inline fun ViewModel.launch(
    coroutineContext: CoroutineContext = CoroutineContextSource().main,
    crossinline block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineContext) { block() }
}

/**
 * @desc Toggle View visibility
 * TODO see if useful
 */
fun View.visible() {
    visibility = View.VISIBLE
}
fun View.gone() {
    visibility = View.GONE
}

/**
 * @desc Simple toast message creation
 * TODO see if useful
 */
fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
