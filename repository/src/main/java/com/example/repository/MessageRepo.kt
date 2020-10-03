package com.example.repository

import com.example.models.chat.EntityChat
import timber.log.Timber

class MessageRepo {
    fun messages(): List<EntityChat>{
        Timber.d("Repo fetch all messages...")
        return emptyList()
    }
}