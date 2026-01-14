package com.example.carlkotlin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carlkotlin.domain.model.Post
import com.example.carlkotlin.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _currentPost = MutableStateFlow<Post?>(null)
    val currentPost: StateFlow<Post?> = _currentPost.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch { repository.getPosts().collect { _posts.value = it } }
    }

    fun getPost(id: String) {
        viewModelScope.launch { _currentPost.value = repository.getPost(id) }
    }

    fun createPost(title: String, content: String) {
        viewModelScope.launch { repository.createPost(title, content) }
    }

    fun updatePost(id: String, title: String, content: String) {
        viewModelScope.launch {
            repository.updatePost(id, title, content)
            getPost(id) // Refresh current post
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch { repository.deletePost(id) }
    }
}
