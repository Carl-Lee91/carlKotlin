package com.example.carlkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carlkotlin.presentation.PostViewModel
import com.example.carlkotlin.presentation.detail.PostDetailScreen
import com.example.carlkotlin.presentation.edit.PostEditScreen
import com.example.carlkotlin.presentation.list.PostListScreen
import com.example.carlkotlin.presentation.navigation.Screen
import com.example.carlkotlin.ui.theme.CarlKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarlKotlinTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.PostList.route) {
                    composable(Screen.PostList.route) {
                        val viewModel: PostViewModel = hiltViewModel()
                        PostListScreen(navController = navController, viewModel = viewModel)
                    }
                    composable(
                            route = Screen.PostDetail.route,
                            arguments = listOf(navArgument("postId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString("postId") ?: ""
                        val viewModel: PostViewModel = hiltViewModel()
                        PostDetailScreen(
                                navController = navController,
                                viewModel = viewModel,
                                postId = postId
                        )
                    }
                    composable(
                            route = Screen.PostEdit.route,
                            arguments =
                                    listOf(
                                            navArgument("postId") {
                                                type = NavType.StringType
                                                nullable = true
                                                defaultValue = null
                                            }
                                    )
                    ) { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString("postId")
                        val viewModel: PostViewModel = hiltViewModel()
                        PostEditScreen(
                                navController = navController,
                                viewModel = viewModel,
                                postId = postId
                        )
                    }
                }
            }
        }
    }
}
