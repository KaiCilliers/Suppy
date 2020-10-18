package com.example.repository.webservice.impl

import com.example.repository.webservice.Confguration
import com.example.repository.webservice.ConnectionListener
import com.example.repository.webservice.Server
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
    val configuration: com.example.repository.webservice.Confguration = com.example.repository.webservice.impl.DefaultConfiguration()
) : com.example.repository.webservice.Server {
    val connection: XMPPTCPConnection by lazy { build() }
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
     * Decided to pass along the listener and not create it here
     * due to Repositories being required in the listeners,
     * because the listeners are the ones that let us know
     * of new messages and any network activity really
     */
    override fun monitor(listener: ConnectionListener) {
        connection.addConnectionListener(listener)
        connection.addAsyncStanzaListener(listener, StanzaFilter { true })
        roster.addRosterListener(listener)
        roster.addRosterLoadedListener(listener)
        roster.addPresenceEventListener(listener)
        roster.addSubscribeListener(listener)
        reconnectManager.enableAutomaticReconnection()
        reconnectManager.addReconnectionListener(listener)
        chatManager.addIncomingListener(listener)
        chatManager.addOutgoingListener(listener)
        Timber.v("Server listeners setup complete...")
    }

    override fun connection(): XMPPTCPConnection = connection

    private fun build(): XMPPTCPConnection = XMPPTCPConnection(configuration.build())
}