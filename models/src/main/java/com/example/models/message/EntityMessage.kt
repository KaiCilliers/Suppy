package com.example.models.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.mapper.DomainMapper

@Entity(tableName = "table_message")
data class EntityMessage(
    @ColumnInfo(name = "message_id")
    val id: String,
    @ColumnInfo(name = "to_bare_jid")
    val toBareJid: String,
    @ColumnInfo(name = "to_jid")
    val toJid: String,
    @ColumnInfo(name = "to_name")
    val toName: String,
    @ColumnInfo(name = "to_resource")
    val toResource: String,
    @ColumnInfo(name = "from_bare_jid")
    val fromBareJid: String,
    @ColumnInfo(name = "from_jid")
    val fromJid: String,
    @ColumnInfo(name = "from_name")
    val fromName: String,
    @ColumnInfo(name = "from_resource")
    val fromResource: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "subject")
    val subject: String,
    @ColumnInfo(name = "from_domain")
    val fromDomain: String,
    @ColumnInfo(name = "error")
    val error: String,
    @ColumnInfo(name = "extensions")
    val extensions: String,
    @ColumnInfo(name = "received")
    val recived: Boolean, // TODO fix spelling mistake
    @ColumnInfo(name = "timestamp")
    val timestamp: String,
    // TODO temp - just used for determining which message arrived last
    @PrimaryKey(autoGenerate = true) // Room allows for single PK which is fine for now
    @ColumnInfo(name = "counter_temp")
    val counter: Int
) : DomainMapper<DomainMessage> {
    // TODO all these harcoded strings can be moved somewhere else
    constructor(xmlMessage: Temp) : this(
        id = xmlMessage.id,
        toBareJid = xmlMessage.to.split('/')[0],
        toJid = xmlMessage.to,
        toName = xmlMessage.to.split('@')[0],
        toResource = "",
        fromBareJid = "scyther@jabber-hosting.de",
        fromJid = "scyther@jabber-hosting.de/MobileAndroid",
        fromName = "scyther",
        fromResource = "MobileAndroid",
        type = xmlMessage.type,
        body = xmlMessage.body,
        subject = "",
        fromDomain = "jabber-hosting.de",
        error = "",
        extensions = "",
        recived = true,
        timestamp = xmlMessage.timestamp,
        counter = 0
    )
    override fun asDomain(): DomainMessage {
        return DomainMessage(
            fromName = fromName,
            toName = toName,
            fromBareJid = fromBareJid,
            subject = subject,
            body = body,
            received = recived,
            timestamp = timestamp
        )
    }
}
// TODO rename and refractor and move to seperate file
data class Temp(
    val id: String,
    val to: String,
    val type: String,
    val body: String,
    val timestamp: String
)