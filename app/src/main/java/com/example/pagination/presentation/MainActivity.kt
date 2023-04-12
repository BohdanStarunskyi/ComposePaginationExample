package com.example.pagination.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pagination.common.constants.Routes
import com.example.pagination.presentation.theme.AppArchitectureTheme
import com.example.pagination.presentation.ui.details.DetailsScreen
import com.example.pagination.presentation.ui.main.MainScreen
import com.example.pagination.presentation.ui.main.states.OffsetState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
        setContent {
            AppArchitectureTheme {
                val navController = rememberNavController()
                val listState = rememberLazyListState()
                val offsetState =
                    remember {
                        mutableStateOf(
                            OffsetState(
                                bottomOffset = getFirstVisibleItemIndex(
                                    listState
                                )
                            )
                        )
                    }
                NavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    startDestination = Routes.MAIN.route
                ) {
                    composable(Routes.MAIN.route) {
                        MainScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            listState = listState,
                            offsetState = offsetState
                        )
                    }
                    composable(
                        route = "${Routes.DETAILS.route}/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) {
                        val id = it.arguments?.getInt("productId")
                        id?.let { productId ->
                            DetailsScreen(modifier = Modifier.fillMaxSize(), id = productId)
                        }
                    }
                }
            }
        }
    }

    private fun getFirstVisibleItemIndex(lazyListState: LazyListState) =
        lazyListState.firstVisibleItemIndex - 10
}