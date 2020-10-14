package com.example.webservice.impl

import com.example.webservice.Confguration
import com.example.webservice.ConnectionListener
import com.example.webservice.Server
import org.jivesoftware.smack.*
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.filter.StanzaFilter
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import timber.log.Timber

/**
 * Basic Server implementation with configurable
 * XMPP connection with a default configuration
 */
class BaseServer(
    val configuration: Confguration = DefaultConfiguration()
) : Server {
    val connection: XMPPTCPConnection by lazy { connection() }
    val roster: Roster by lazy { Roster.getInstanceFor(connection) }
    val reconnectManager: ReconnectionManager by lazy { ReconnectionManager.getInstanceFor(connection) }
    val chatManager: ChatManager by lazy { ChatManager.getInstanceFor(connection) }

    override fun login(): XMPPTCPConnection {
        connection.connect().login()
        return connection
    }

    /**
     * TODO determine if necessary
     */
    override fun connect() {
        connection.connect()
    }
    /**
     * Listeners to better monitor network activity
     */
    override fun monitor() {
        connection.addConnectionListener(ConnectionListener())
        connection.addAsyncStanzaListener(ConnectionListener(), StanzaFilter { true })
        roster.addRosterListener(ConnectionListener())
        roster.addRosterLoadedListener(ConnectionListener())
        roster.addPresenceEventListener(ConnectionListener())
        roster.addSubscribeListener(ConnectionListener())
        reconnectManager.enableAutomaticReconnection()
        reconnectManager.addReconnectionListener(ConnectionListener())
        chatManager.addIncomingListener(ConnectionListener())
        chatManager.addOutgoingListener(ConnectionListener())
        Timber.v("Server listeners setup complete...")
    }

    private fun connection(): XMPPTCPConnection = XMPPTCPConnection(configuration.build())
}