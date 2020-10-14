package com.example.webservice.impl

import com.example.webservice.configuration.AuthDetails
import com.example.webservice.Confguration
import com.example.webservice.configuration.DomainDetails
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

/**
 * Basic implementation of [Confguration]
 */
class BaseConfiguration(
    val domain: DomainDetails,
    val authDetails: AuthDetails,
    val resource: String,
    val timeout: Int
) : Confguration {
    override fun build(): XMPPTCPConnectionConfiguration =
        XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(authDetails.username, authDetails.password)
            .setXmppDomain(domain.xmpp)
            .setHost(domain.host)
            .setPort(domain.port)
            .setResource(resource)
            .setConnectTimeout(timeout)
            .build()
}