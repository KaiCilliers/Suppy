package com.example.webservice.impl

import com.example.webservice.configuration.AuthDetails
import com.example.webservice.Confguration
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

/**
 * An implementation of [Confguration]
 * with preset default values.
 * Optional login details can be set
 */
class DefaultConfiguration(
    val authDetails: AuthDetails = AuthDetails(
        "scyther",
        "1234"
    )
) : Confguration {
    override fun build(): XMPPTCPConnectionConfiguration =
        XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(authDetails.username, authDetails.password)
            .setXmppDomain("jabber-hosting.de")
            .setHost("jabber-hosting.de")
            .setPort(5222)
            .setResource("MobileAndroid")
            .setConnectTimeout(15000)
            .build()
}