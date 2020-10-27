package com.example.models.message.stanza

data class Message(
    val id: String,
    val type: String,
    val body: String,
    val subject: String
)