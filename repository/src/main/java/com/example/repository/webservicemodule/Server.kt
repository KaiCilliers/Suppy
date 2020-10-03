package com.example.repository.webservicemodule

import org.jivesoftware.smack.ReconnectionManager
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

    fun build(): XMPPTCPConnection {
        Timber.d("Building server connection...")
        config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword("scyther@jabber-hosting.de", "1234")
            .setXmppDomain("jabber-hosting.de")
            .setHost("jabber-hosting.de")
            .setPort(5222)
            .setResource("MobileAndroid")
            .build()
        connection = XMPPTCPConnection(config)
        roster = Roster.getInstanceFor(connection)
        reconnectManager = ReconnectionManager.getInstanceFor(connection)
        setupListeners()
        connection.connect().login()
        return connection
    }

    /**
     * Listeners to better monitor network activity
     */
    private fun setupListeners() {
        connection.addConnectionListener(ConnectionListener())
        connection.addStanzaAcknowledgedListener(ConnectionListener())
        connection.addStanzaDroppedListener(ConnectionListener())
        roster.addRosterListener(ConnectionListener())
        roster.addSubscribeListener(ConnectionListener())
        roster.addRosterLoadedListener(ConnectionListener())
        roster.addPresenceEventListener(ConnectionListener())
        reconnectManager.enableAutomaticReconnection()
        reconnectManager.addReconnectionListener(ConnectionListener())
    }

    /**
     * Single instance of server connection
     */
    companion object {
        private lateinit var INSTANCE: XMPPTCPConnection
        fun instance(): XMPPTCPConnection {
            synchronized(Server::class.java) {
                if(!::INSTANCE.isInitialized) {
                    INSTANCE = Server().build()
                }
            }
            Timber.d("Returning instance of server...")
            return INSTANCE
        }
    }
}