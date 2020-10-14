package com.example.webservice

import org.jivesoftware.smack.tcp.XMPPTCPConnection

/**
 *  Represents a XMPP connection
 */
interface Server {
    fun login(): XMPPTCPConnection
    fun connect()
    fun monitor(listener: ConnectionListener)
}