package com.example.carlkotlin.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carlkotlin.presentation.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEditScreen(navController: NavController, viewModel: PostViewModel, postId: String?) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val currentPost by viewModel.currentPost.collectAsState()

    LaunchedEffect(postId) {
        if (postId != null && postId.isNotEmpty()) {
            viewModel.getPost(postId)
        } else {}
    }

    LaunchedEffect(currentPost) {
        if (postId != null && currentPost != null && currentPost!!.id == postId) {
            title = currentPost!!.title
            content = currentPost!!.content
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = {
                            Text(if (postId.isNullOrEmpty()) "Create Post" else "Edit Post")
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                )
                            }
                        }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                        onClick = {
                            if (title.isNotBlank() && content.isNotBlank()) {
                                if (postId != null && postId.isNotEmpty()) {
                                    viewModel.updatePost(postId, title, content)
                                } else {
                                    viewModel.createPost(title, content)
                                }
                                navController.popBackStack()
                            }
                        }
                ) { Icon(imageVector = Icons.Default.Check, contentDescription = "Save") }
            }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}
