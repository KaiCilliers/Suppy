package com.example.repository.webservice

import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

/**
 * Represents connection configuration
 * for an XMPP connection to be used
 * with [Server] interface
 */
interface Confguration {
    fun build(): XMPPTCPConnectionConfiguration
}