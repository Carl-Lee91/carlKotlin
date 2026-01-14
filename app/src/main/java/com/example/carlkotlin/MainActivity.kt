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

                // No more manual factory creation!
                // Hilt provides the ViewModel automatically.

                NavHost(navController = navController, startDestination = Screen.PostList.route) {
                    composable(Screen.PostList.route) {
                        // Use hiltViewModel() to get the ViewModel scoped to this navigation graph
                        // entry
                        val viewModel: PostViewModel = hiltViewModel()
                        PostListScreen(navController = navController, viewModel = viewModel)
                    }
                    composable(
                            route = Screen.PostDetail.route,
                            arguments = listOf(navArgument("postId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString("postId") ?: ""
                        // Getting the SAME instance if we scoped it properly, but here we just get
                        // a fresh one or existing one depending on scope.
                        // Actually, hiltViewModel() inside a composable returns a ViewModel scoped
                        // to the closest ViewModelStoreOwner.
                        // In Navigation compose, each backStackEntry is a ViewModelStoreOwner.
                        // So calling hiltViewModel() in detail screen creates a NEW ViewModel or
                        // gets one for that entry.
                        // Since our ViewModel basically just loads data from Singleton Repository,
                        // multiple ViewModels are fine for now.
                        // Optimally, we might want to share, but for this simple CRUD, per-screen
                        // ViewModel usage is acceptable OR we can pass it.
                        // But standard Compose Nav pattern is letting each screen get its own VM or
                        // scoping to a nested graph.
                        // Here we just let it get its own instance.
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
