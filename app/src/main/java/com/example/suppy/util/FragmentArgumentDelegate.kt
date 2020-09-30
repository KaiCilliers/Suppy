package com.example.suppy.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate to handle fragment bundle reads and writes
 * TODO see if this is usable for this project
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