package com.example.models.chat

data class DomainChat(
    private val chatName: String,
    private val lastActivity: String,
    private val mute: Boolean,
    private val description: String,
    private val creator: String,
    private val createdAt: String
)