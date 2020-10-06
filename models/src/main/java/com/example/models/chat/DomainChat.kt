package com.example.models.chat

data class DomainChat(
    val id: Int,
    val chatName: String,
    val lastActivity: String,
    val mute: Boolean,
    val description: String,
    val creator: String,
    val createdAt: String
)
