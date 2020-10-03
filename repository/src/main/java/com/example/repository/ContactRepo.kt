package com.example.repository

import com.example.models.chat.EntityChat
import timber.log.Timber

class ContactRepo {
    fun contacts(): List<EntityChat>{
        Timber.d("Repo fetch all contacts...")
        return emptyList()
    }
}