package com.example.models.message.stanza

data class From(
    val jid: Jid,
    val name: String,
    val resource: String,
    val domain: String
)