package com.example.repository

import com.example.models.chat.EntityChat
import timber.log.Timber

class ChatRepo {
    fun chats(): List<EntityChat>{
        Timber.d("Repo fetch all chats...")
        return emptyList()
    }
}