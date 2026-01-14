package com.example.carlkotlin.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carlkotlin.presentation.PostViewModel
import com.example.carlkotlin.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(navController: NavController, viewModel: PostViewModel, postId: String) {
    val post by viewModel.currentPost.collectAsState()

    LaunchedEffect(postId) { viewModel.getPost(postId) }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Post Detail") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                    onClick = {
                                        navController.navigate(Screen.PostEdit.createRoute(postId))
                                    }
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(
                                    onClick = {
                                        viewModel.deletePost(postId)
                                        navController.popBackStack()
                                    }
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                )
                            }
                        }
                )
            }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            post?.let {
                Text(text = it.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.content, style = MaterialTheme.typography.bodyLarge)
            }
                    ?: run { Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge) }
        }
    }
}
