package com.example.models.message.stanza

data class MetaData(
    val error: String,
    val extensions: String,
    val received: Boolean,
    val timestamp: String
)