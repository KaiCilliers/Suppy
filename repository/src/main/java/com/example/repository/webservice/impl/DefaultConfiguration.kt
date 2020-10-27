package com.example.repository.webservice.impl

import com.example.repository.webservice.configuration.AuthDetails
import com.example.repository.webservice.Confguration
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

/**
 * An implementation of [Confguration]
 * with preset default values.
 * Optional login details can be set
 */
class DefaultConfiguration(
    val authDetails: com.example.repository.webservice.configuration.AuthDetails = com.example.repository.webservice.configuration.AuthDetails(
        "scyther",
        "1234"
    )
) : com.example.repository.webservice.Confguration {
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