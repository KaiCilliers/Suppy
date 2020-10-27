package com.example.models.message

import com.example.models.mapper.RoomMapper
import com.example.models.message.stanza.From
import com.example.models.message.stanza.Message
import com.example.models.message.stanza.MetaData
import com.example.models.message.stanza.To

data class StanzaMessage(
    private val to: To,
    private val from: From,
    private val message: Message,
    private val metaData: MetaData
) : RoomMapper<EntityMessage> {
    override fun asRoom(): EntityMessage {
        return EntityMessage(
            id = message.id,
            toBareJid = to.jid.bare,
            toJid = to.jid.full,
            toName = to.name,
            toResource = to.resource,
            fromBareJid = from.jid.bare,
            fromJid = from.jid.full,
            fromName = from.name,
            fromResource = from.resource,
            type = message.type,
            body = message.body,
            subject = message.subject,
            fromDomain = from.domain,
            error = metaData.error,
            extensions = metaData.extensions,
            recived = metaData.received,
            timestamp = metaData.timestamp,
            counter = 0
        )
    }
}