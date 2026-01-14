package com.example.carlkotlin.presentation.navigation

sealed class Screen(val route: String) {
    object PostList : Screen("post_list")
    object PostDetail : Screen("post_detail/{postId}") {
        fun createRoute(postId: String) = "post_detail/$postId"
    }
    object PostEdit : Screen("post_edit?postId={postId}") {
        fun createRoute(postId: String? = null) = "post_edit?postId=${postId ?: ""}"
    }
}
