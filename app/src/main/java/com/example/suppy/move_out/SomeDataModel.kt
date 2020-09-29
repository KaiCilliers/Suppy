package com.example.suppy.move_out

import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

data class SomeDataModel(
    val name: String,
    val description: String
) {
    fun meth() {
        Timber.d("$name and $description")
    }
}