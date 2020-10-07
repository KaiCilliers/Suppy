package com.example.models.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.mapper.DomainMapper

@Entity(tableName = "table_message")
data class EntityMessage(
    @PrimaryKey
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
    @ColumnInfo(name = "timestamp")
    val timestamp: String
) : DomainMapper<DomainMessage> {
    override fun asDomain(): DomainMessage {
        return DomainMessage(
            fromName = fromName,
            toName = toName,
            fromBareJid = fromBareJid,
            subject = subject,
            body = body,
            timestamp = timestamp
        )
    }
}