package com.example.repository.webservicemodule

import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionListener
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.XMPPConnection
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
    ReconnectionListener {
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
        for(entry in roster!!.entries) {
            Timber.d("$entry")
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