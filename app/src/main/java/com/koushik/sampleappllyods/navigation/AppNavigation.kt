package com.koushik.sampleappllyods.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.koushik.sampleappllyods.R
import com.koushik.sampleappllyods.ui.screen.ErrorMessage
import com.koushik.sampleappllyods.ui.screen.ItemDetailScreen
import com.koushik.sampleappllyods.ui.screen.ItemListScreen
import com.koushik.sampleappllyods.viewmodel.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "item_list_screen"
    ) {
        composable("item_list_screen") {
            ItemListScreen(viewModel = viewModel) { selectedItem ->
                navController.navigate("item_detail_screen/${selectedItem.articleId}")
            }
        }
        composable(
            route = "item_detail_screen/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val selectedItem = viewModel.getItemById(itemId)
            selectedItem?.let {
                ItemDetailScreen(item = it)
            } ?: ErrorMessage(error = stringResource(R.string.item_not_found), onRetry = { navController.popBackStack() })
        }
    }
}