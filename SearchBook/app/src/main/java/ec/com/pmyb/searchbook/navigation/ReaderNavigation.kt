package ec.com.pmyb.searchbook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ec.com.pmyb.searchbook.screens.ReaderSplashScreen
import ec.com.pmyb.searchbook.screens.details.BookDetailsScreen
import ec.com.pmyb.searchbook.screens.home.Home
import ec.com.pmyb.searchbook.screens.home.HomeScreenViewModel
import ec.com.pmyb.searchbook.screens.login.ReaderLoginScreen
import ec.com.pmyb.searchbook.screens.search.BookSearchViewModel
import ec.com.pmyb.searchbook.screens.search.SearcheScreen
import ec.com.pmyb.searchbook.screens.stats.ReaderStatsScreen
import ec.com.pmyb.searchbook.screens.update.BookUpdateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }
        composable(ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearcheScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }

        }
    }
}