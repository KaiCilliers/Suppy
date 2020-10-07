package com.example.models.message

import com.example.models.mapper.RoomMapper

data class StanzaMessage(
    private val id: String,
    private val toBareJid: String,
    private val toJid: String,
    private val toName: String,
    private val toResource: String,
    private val fromBareJid: String,
    private val fromJid: String,
    private val fromName: String,
    private val fromResource: String,
    private val type: String,
    private val body: String,
    private val subject: String,
    private val fromDomain: String,
    private val error: String,
    private val extensions: String,
    private val received: Boolean,
    private val timestamp: String
) : RoomMapper<EntityMessage> {
    override fun asRoom(): EntityMessage {
        return EntityMessage(
            id = id,
            toBareJid = toBareJid,
            toJid = toJid,
            toName = toName,
            toResource = toResource,
            fromBareJid = fromBareJid,
            fromJid = fromJid,
            fromName = fromName,
            fromResource = fromResource,
            type = type,
            body = body,
            subject = subject,
            fromDomain = fromDomain,
            error = error,
            extensions = extensions,
            recived = received,
            timestamp = timestamp
        )
    }
}