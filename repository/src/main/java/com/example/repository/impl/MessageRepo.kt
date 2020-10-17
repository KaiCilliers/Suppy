package com.example.repository.impl

import androidx.lifecycle.LiveData
import com.example.database.message.MessageDao
import com.example.models.message.EntityMessage
import com.example.models.message.Temp
import com.example.models.message.UpdatedReceived
import com.example.repository.MessageRepository
import com.example.repository.webservice.Server
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.impl.JidCreate
import timber.log.Timber
import java.util.*
import kotlin.random.Random

class MessageRepo(private val dao: MessageDao, private val xmpp: Server) : MessageRepository {
    /**
     * Send a message
     */
    // TODO this is a tester method, it needs a lot of work
    // TODO lots of hardcoded strings
    suspend fun send(msgs: String) {
        val msg = Message()
        msg.type = Message.Type.chat
        msg.body = "My favourite number is: ${Random.nextInt(9999)}" +
                " oh and the actual message: $msg"
        val msgTwo = Message(
            JidCreate.bareFrom("weedle@jabber-hosting.de"),
            Message.Type.chat
        )
        msgTwo.body = "I dislike the ${Random.nextInt(444)} rats in my garden" +
                " oh and the actual message: $msgs"
        Timber.d("My message as XML: ${msgTwo.toXML("hotdog")}")
        xmpp.connection().sendStanza(msgTwo)
        val temp = Temp(
            "${msgTwo.stanzaId}",
            "${msgTwo.to}",
            "${msgTwo.type}",
            "${msgTwo.body}",
            "${Date()}"
        )
        Timber.d("TEMP VALUE MESSAGE: $temp")
        val et = EntityMessage(temp)
        Timber.d("ENTITY FROM TEMP MSG: $et")
        dao.insert(et)
    }

    fun messages(): LiveData<List<EntityMessage>>{
        Timber.d("Repo fetch all messages...")
        return dao.all()
    }
    suspend fun insert(message: EntityMessage) {
        dao.insert(message)
    }

    fun latestMessage(): LiveData<EntityMessage> {
      return dao.latestMessage()
    }

    /**
     * Get all unreceived messages wrapped in LiveData
     */
    fun getAllUnreceivedLiveData(): LiveData<List<EntityMessage>> {
        return dao.getAllUnReceivedLiveData()
    }

    /**
     * Return all unreceived messages as just a list
     * TODO temp
     */
    fun getAllUnreceived(): List<EntityMessage> {
        return dao.getAllUnReceived()
    }

    /**
     * Return all the messages from a specific chat
     */
    fun allMessagesFrom(chatName: String): LiveData<List<EntityMessage>>{
        Timber.d("Repo fetching all messages form $chatName")
        return dao.allMessagesFrom(chatName)
    }
    /**
     * Return all chats from a chat
     */
    fun chatMessages(chatName: String): LiveData<List<EntityMessage>> {
        Timber.d("Fetching all messages from chat: $chatName (TO AND FROM)")
        return dao.chatMessages(chatName)
    }
    /**
     * Update description of chat item using an
     * updated partial object entity
     */
    suspend fun updateReceivedValue(updated: UpdatedReceived) {
        Timber.d("Partial message object: $updated")
        dao.updateReceived(updated)
    }

    /**
     * Fetch messages without LiveData wrapper for
     * debugging purposes
     * TODO temp
     */
    suspend fun justMessages(): List<EntityMessage> {
        Timber.d("Message REpo called to fetch local messages...")
        return dao.justAll()
    }

    /**
     * Set all messages in specific chat as received
     */
    suspend fun updateAllMessagesFromChatReceived(chatName: String) {
        Timber.d("Updating all messages from \"$chatName\" to received")
        dao.updateAllReceivedFromChat(chatName, true)
    }
}