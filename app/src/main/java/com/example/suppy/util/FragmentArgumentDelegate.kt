package com.example.suppy.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate to handle fragment bundle reads and writes
 * @sample var param1: Int by argument()
 * @author https://proandroiddev.com/kotlin-delegates-in-android-1ab0a715762d
 */
class FragmentArgumentDelegate<T : Any> : ReadWriteProperty<Fragment, T> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments
            ?.get(property.name) as? T
            ?: throw Exception("Property ${property.name} could not be read")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val args = thisRef.arguments ?: Bundle().also(thisRef::setArguments)
        args.put(
            property.name,
            value
        )
    }
}