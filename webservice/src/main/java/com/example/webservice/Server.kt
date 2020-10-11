package com.example.webservice

import org.jivesoftware.smack.*
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.filter.StanzaFilter
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import timber.log.Timber

class Server {
    /**
     * TODO cleanup this with delegates or something
     */
    lateinit var connection: XMPPTCPConnection
    lateinit var config: XMPPTCPConnectionConfiguration
    lateinit var roster: Roster
    lateinit var reconnectManager: ReconnectionManager
    lateinit var chatManager: ChatManager

    fun build(): XMPPTCPConnection {
        Timber.d("Building server connection...")
        config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword("scyther", "1234")
            .setXmppDomain("jabber-hosting.de")
            .setHost("jabber-hosting.de")
            .setPort(5222)
            .setResource("MobileAndroid")
            .setConnectTimeout(15000)
            .build()
        Timber.v("Server configured...")
        connection = XMPPTCPConnection(config)
        Timber.v("Connection prepped...")
        roster = Roster.getInstanceFor(connection)
        Timber.v("Roster instance established...")
        reconnectManager = ReconnectionManager.getInstanceFor(connection)
        Timber.v("Reconnection manager instance established...")
        chatManager = ChatManager.getInstanceFor(connection)
        setupListeners()
        connection.connect().login()
        Timber.v("Logged in...")
        return connection
    }

    /**
     * Listeners to better monitor network activity
     */
    private fun setupListeners() {
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

    /**
     * Single instance of server connection
     */
    companion object {
        private lateinit var INSTANCE: XMPPTCPConnection
        fun instance(): XMPPTCPConnection {
            synchronized(Server::class.java) {
                if(!::INSTANCE.isInitialized) {
                    INSTANCE = Server()
                        .build()
                }
            }
            Timber.d("Returning instance of server...")
            return INSTANCE
        }
    }
}