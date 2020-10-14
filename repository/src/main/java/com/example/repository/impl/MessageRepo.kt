package com.example.repository.impl

import androidx.lifecycle.LiveData
import com.example.database.message.MessageDao
import com.example.models.message.EntityMessage
import com.example.models.message.UpdatedReceived
import com.example.repository.MessageRepository
import com.example.repository.webservice.Server
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.impl.JidCreate
import timber.log.Timber
import kotlin.random.Random

class MessageRepo(val dao: MessageDao, val xmpp: Server) : MessageRepository {
    fun send() {
        val msg = Message()
        msg.type = Message.Type.chat
        msg.body = "My favourite number is: ${Random.nextInt(9999)}"
        val msgTwo = Message(
            JidCreate.bareFrom("weedle@jabber-hosting.de"),
            Message.Type.chat
        )
        msgTwo.body = "I dislike the ${Random.nextInt(444)} rats in my garden"
        Timber.d("My message as XML: ${msgTwo.toXML("hotdog")}")
        xmpp.connection().sendStanza(msgTwo)
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