package com.example.repository.webservice.impl

import com.example.repository.webservice.configuration.AuthDetails
import com.example.repository.webservice.Confguration
import com.example.repository.webservice.configuration.DomainDetails
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

/**
 * Basic implementation of [Confguration]
 */
class BaseConfiguration(
    val domain: com.example.repository.webservice.configuration.DomainDetails,
    val authDetails: com.example.repository.webservice.configuration.AuthDetails,
    val resource: String,
    val timeout: Int
) : com.example.repository.webservice.Confguration {
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