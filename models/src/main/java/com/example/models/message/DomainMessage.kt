package com.example.models.message


data class DomainMessage(
    val fromName: String,
    val toName: String,
    val fromBareJid: String,
    val subject: String,
    val body: String,
    val received: Boolean,
    val timestamp: String
)