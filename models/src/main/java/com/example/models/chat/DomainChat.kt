package com.example.models.chat

import com.example.models.chat.domain.Identification
import com.example.models.chat.domain.MetaData
import com.example.models.chat.domain.Status

data class DomainChat(
    val indentification: Identification,
    val status: Status,
    val metadata: MetaData
)
