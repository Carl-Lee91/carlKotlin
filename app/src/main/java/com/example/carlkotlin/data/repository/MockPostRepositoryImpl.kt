package com.example.carlkotlin.data.repository

import com.example.carlkotlin.domain.model.Post
import com.example.carlkotlin.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MockPostRepositoryImpl : PostRepository {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    override fun getPosts(): Flow<List<Post>> = _posts.asStateFlow()

    override suspend fun getPost(id: String): Post? {
        return _posts.value.find { it.id == id }
    }

    override suspend fun createPost(title: String, content: String) {
        val newPost = Post(title = title, content = content)
        _posts.update { currentPosts -> currentPosts + newPost }
    }

    override suspend fun updatePost(id: String, title: String, content: String) {
        _posts.update { currentPosts ->
            currentPosts.map {
                if (it.id == id) {
                    it.copy(title = title, content = content)
                } else {
                    it
                }
            }
        }
    }

    override suspend fun deletePost(id: String) {
        _posts.update { currentPosts -> currentPosts.filterNot { it.id == id } }
    }
}
