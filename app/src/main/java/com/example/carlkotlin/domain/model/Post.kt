package com.example.carlkotlin.domain.model

import java.util.UUID

data class Post(
        val id: String = UUID.randomUUID().toString(),
        val title: String,
        val content: String,
        val createdAt: Long = System.currentTimeMillis()
)
