package com.example.repository.webservicemodule

import org.jivesoftware.smack.*
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

    fun someTestingCode() {
        XMPPConnectionRegistry.addConnectionCreationListener(ConnectionListener())
    }

    fun build(): XMPPTCPConnection {
        Timber.d("Building server connection...")
        config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword("scyther", "1234")
            .setXmppDomain("jabber-hosting.de")
            .setHost("jabber-hosting.de")
            .setPort(5222)
            .setResource("MobileAndroid")
            .build()
        Timber.v("Server configured...")
        connection = XMPPTCPConnection(config)
        Timber.v("Connection prepped...")
        roster = Roster.getInstanceFor(connection)
        Timber.v("Roster instance established...")
        reconnectManager = ReconnectionManager.getInstanceFor(connection)
        Timber.v("Reconnection manager instance established...")
        setupListeners()
        connection.connect().login()
        Timber.v("Logged in...")
        return connection
    }

    /**
     * Listeners to better monitor network activity
     */
    private fun setupListeners() {
        connection.addConnectionListener(AbstractConnectionListener())
        connection.addStanzaAcknowledgedListener(ConnectionListener())
        connection.addStanzaDroppedListener(ConnectionListener())
        roster.addRosterListener(ConnectionListener())
        roster.addSubscribeListener(ConnectionListener())
        roster.addRosterLoadedListener(ConnectionListener())
        roster.addPresenceEventListener(ConnectionListener())
        reconnectManager.enableAutomaticReconnection()
        reconnectManager.addReconnectionListener(ConnectionListener())
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
                    INSTANCE = Server().build()
                }
            }
            Timber.d("Returning instance of server...")
            return INSTANCE
        }
    }
}