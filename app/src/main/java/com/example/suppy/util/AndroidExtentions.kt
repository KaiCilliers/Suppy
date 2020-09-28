package com.example.suppy.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

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