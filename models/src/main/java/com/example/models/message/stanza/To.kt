package com.example.models.message.stanza

data class To(
    val jid: Jid,
    val name: String,
    val resource: String
)