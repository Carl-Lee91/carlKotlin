package com.example.carlkotlin.domain.repository

import com.example.carlkotlin.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun getPost(id: String): Post?
    suspend fun createPost(title: String, content: String)
    suspend fun updatePost(id: String, title: String, content: String)
    suspend fun deletePost(id: String)
}
