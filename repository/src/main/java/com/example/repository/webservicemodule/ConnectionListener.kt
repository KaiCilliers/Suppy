package com.example.repository.webservicemodule

import com.example.models.RosterEntry
import com.example.models.RosterGroup
import com.example.models.chat.EntityChat
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
        val entries = arrayListOf<RosterEntry>()
        roster!!.entries.forEach {contact ->
            val entry = RosterEntry(
                name = contact.name,
                subType = "${contact.type}",
                bareJid = "${contact.jid}",
                approved = contact.isApproved,
                subPending = contact.isSubscriptionPending,
                commonGroups = arrayListOf()
            )
            contact.groups.forEach{group ->
                val groupEntry = RosterGroup(
                    name = group.name,
                    entries = arrayListOf(),
                    memberCount = group.entryCount
                )
                group.entries.forEach {
                    groupEntry.add(it.name)
                }
                entry.add(groupEntry)
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
//        MainScope().launch {
//            withContext(Dispatchers.IO) {
//                Timber.d("Repopulating database")
//                ChatRepo().repopulate(roomChats)
//            }
//        }
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
        Timber.d(".\n$message \nfrom $from" +
                "\nBody: ${message.body} " +
                "\nType: ${message.type} " +
                "\nSubject: ${message.subject} " +
                "\nFrom domain: ${from?.domain} " +
                "\nTo: ${message.to} " +
                "\nThread: ${message.thread} " +
                "\nError: ${message.error} " +
                "\nLanguage: ${message.language} " +
                "\nExtensions: follow: ")
        message.extensions.forEach {
            Timber.d("${it.namespace} and element = ${it.elementName}")
        }
        Timber.d("Complete message: $message")
        Timber.d("Complete from: $from")
        Timber.d("Complete chat: $chat")
        Timber.d("${chat?.xmppAddressOfChatPartner}")
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