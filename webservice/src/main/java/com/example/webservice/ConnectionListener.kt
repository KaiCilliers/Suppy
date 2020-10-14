package com.example.webservice

import com.example.database.database.LocalDatabase
import com.example.models.chat.roster.impl.BasicRosterEntry
import com.example.models.chat.roster.impl.BasicRosterGroup
import com.example.models.chat.EntityChat
import com.example.models.chat.roster.impl.Identification
import com.example.models.chat.roster.impl.Status
import com.example.models.message.StanzaMessage
import com.example.models.message.stanza.From
import com.example.models.message.stanza.MetaData
import com.example.models.message.stanza.To
import com.example.models.message.stanza.Jid as dataJid
import com.example.models.message.stanza.Message as dataMessage
import com.example.repository.ChatRepo
import com.example.repository.MessageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.*
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.roster.*
import org.jivesoftware.smackx.muc.InvitationListener
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.packet.MUCUser
import org.jxmpp.jid.*
import timber.log.Timber
import java.lang.Exception
import java.util.*

/**
 * This mammoth of a class is used as a collection of listeners to
 * add to the server instance to monitor how the Smack library
 * works
 * TODO Break this class into smaller classes and keep useful listeners
 */
class ConnectionListener : ConnectionListener,
    RosterListener, RosterLoadedListener, SubscribeListener,
    OutgoingChatMessageListener, IncomingChatMessageListener,
    PresenceEventListener, StanzaListener, InvitationListener,
    ReconnectionListener, ConnectionCreationListener, MessageListener {

    /**
     * [MessageListener]
     */

    override fun processMessage(message: Message?) {
        Timber.d("Process messages: $message")
    }

    /**
     * [ConnectionCreationListener]
     */
    override fun connectionCreated(connection: XMPPConnection?) {
        Timber.d("Connection created: $connection...")
    }

    /**
     * [ReconnectionListener]
     */
    override fun reconnectionFailed(e: Exception?) {
        Timber.e("Reconnection failure...")
    }

    override fun reconnectingIn(seconds: Int) {
        Timber.d("Reconnecting in $seconds...")
    }

    /**
     * [ConnectionListener]
     */
    override fun connected(connection: XMPPConnection?) {
        Timber.d("XMPP connection opened...")
    }

    override fun connectionClosed() {
        Timber.d("XMPP connection closed...")
        // YOu can manually call reconnection code here
    }

    override fun connectionClosedOnError(e: Exception?) {
        Timber.e("XMPP connection closed due to error...$e")
    }

    override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
        Timber.d("Account authenticated...")
    }

    /**
     * [RosterListener]
     */
    override fun entriesDeleted(addresses: MutableCollection<Jid>?) {
        Timber.d("Roster - entries deleted: $addresses")
    }

    override fun presenceChanged(presence: Presence?) {
        Timber.d("Roster - presence changed to: $presence")
    }

    override fun entriesUpdated(addresses: MutableCollection<Jid>?) {
        Timber.d("Roster - entries updated: $addresses")
    }

    override fun entriesAdded(addresses: MutableCollection<Jid>?) {
        Timber.d("Roster - entries added: $addresses")
    }

    /**
     * [RosterLoadedListener]
     */
    override fun onRosterLoaded(roster: Roster?) {
        Timber.d("Roster - roster loaded: $roster. Individual entries follow:")
        val entries = arrayListOf<BasicRosterEntry>()
        roster!!.entries.forEach {contact ->
            val entry = BasicRosterEntry(
                /**
                 * consider having two separate fields
                 * one for display and one for identifying
                 * chat by name instead of id in order
                 * to get the id
                 */
                identification = Identification(
                    name = contact.jid.split('@')[0],
                    bareJid = "${contact.jid}"
                ),
                subscriptionType = "${contact.type}",
                status = Status(
                    approved = contact.isApproved,
                    subscriptionPending = contact.isSubscriptionPending
                ),
                commonGroups = arrayListOf()
            )
            contact.groups.forEach{group ->
                val groupEntry =
                    BasicRosterGroup(
                        name = group.name,
                        entries = arrayListOf(),
                        memberCount = group.entryCount
                    )
                group.entries.forEach {
                    groupEntry.add(it.name)
                }
                entry.addGroup(groupEntry)
            }
            entries.add(entry)
        }
        Timber.d("Saved Entries follow:")
        for (value in entries) {
            Timber.d("$value")
            Timber.d("${value.asRoom()}")
        }
        val roomChats = arrayListOf<EntityChat>()
        entries.forEach {
            roomChats.add(it.asRoom())
        }
        /**
         * Check if chat table is empty
         * and if so populate it with Roster
         * data. It will be empty mostly due
         * to database schema changes
         * TODO determine if it works
         */
        MainScope().launch {
            if (ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).isEmpty()) {
                withContext(Dispatchers.IO) {
                    Timber.d("Repopulating database")
                    ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).repopulate(roomChats)
                }
            }
        }
    }

    override fun onRosterLoadingFailed(exception: Exception?) {
        Timber.d("Roster - roster failed to load: $exception")
    }

    /**
     * [SubscribeListener]
     */
    override fun processSubscribe(
        from: Jid?,
        subscribeRequest: Presence?
    ): SubscribeListener.SubscribeAnswer {
        Timber.d("Incoming request from $from to subscribe to my presence: $subscribeRequest")
        return SubscribeListener.SubscribeAnswer.Approve
    }

    /**
     * [IncomingChatMessageListener]
     */
    override fun newIncomingMessage(
        from: EntityBareJid?,
        message: Message?,
        chat: org.jivesoftware.smack.chat2.Chat?
    ) {
        Timber.d("New incoming message from: $from, message: '${message!!.body}' in chat:$chat")
        val msg = StanzaMessage(
            to = To(
                jid = dataJid(
                    bare = message.to.split('/')[0],
                    full = "${message.to}"
                ),
                name = message.to.split('@')[0],
                resource = message.to.split('/')[1]
            ),
            from = From(
                jid = dataJid(
                    bare = "${message.from.split("/")[0]}",
                    full = "${message.from}"
                ),
                name = message.from.split('@')[0],
                resource = message.from.split('/')[1],
                domain = "${from?.domain}"
            ),
            message = dataMessage(
                id = message.stanzaId,
                type = "${message.type}",
                body = message.body,
                subject = "${message.subject}"
            ),
            metaData = MetaData(
                error = "${message.error}",
                extensions = "${message.extensions}",
                received = false,
                timestamp = "${Date()}"
            )
        )
        Timber.d("Captured Stanza Message: $msg")
        Timber.d("Converted Room Message: ${msg.asRoom()}")
        MainScope().launch {
            withContext(Dispatchers.IO) {
                Timber.d("Adding new message to database...")
                MessageRepo.instance(LocalDatabase.justgetinstance().messageDao()).insert(msg.asRoom())
            }
        }
    }

    /**
     * [OutgoingChatMessageListener]
     */
    override fun newOutgoingMessage(
        to: EntityBareJid?,
        message: Message?,
        chat: org.jivesoftware.smack.chat2.Chat?
    ) {
        Timber.d("New outgoing message to: $to, message: '${message!!.body}' in chat: $chat, ")
    }

    /**
     * [PresenceEventListener]
     */
    override fun presenceAvailable(address: FullJid?, availablePresence: Presence?) {
        Timber.d("Presence available received with Address: $address, and Presence: $availablePresence")
    }

    override fun presenceSubscribed(address: BareJid?, subscribedPresence: Presence?) {
        Timber.d("Presence subscribed with address $address and subscribed presence: $subscribedPresence")
    }

    override fun presenceError(address: Jid?, errorPresence: Presence?) {
        Timber.d("Presence error received with address: $address and error presence: $errorPresence")
    }

    override fun presenceUnsubscribed(address: BareJid?, unsubscribedPresence: Presence?) {
        Timber.d("Presence unsubscribed with address: $address and unsubscribed presence: $unsubscribedPresence")
    }

    override fun presenceUnavailable(address: FullJid?, presence: Presence?) {
        Timber.d("Presence unavailable received with address: $address and presence: $presence")
    }

    /**
     * [StanzaListener]
     */
    override fun processStanza(packet: Stanza?) {
        Timber.d("New Stanza with packet: $packet")
    }

    /**
     * [InvitationListener]
     */
    override fun invitationReceived(
        conn: XMPPConnection?,
        room: MultiUserChat?,
        inviter: EntityJid?,
        reason: String?,
        password: String?,
        message: Message?,
        invitation: MUCUser.Invite?
    ) {
        Timber.d("You got an invite! Details to follow" +
                "\n connection: $conn" +
                "\n room: $room" +
                "\n inviter: $inviter" +
                "\n reason: $reason" +
                "\n password: $password" +
                "\n message: $message" +
                "\n invitation: $invitation")
    }
}