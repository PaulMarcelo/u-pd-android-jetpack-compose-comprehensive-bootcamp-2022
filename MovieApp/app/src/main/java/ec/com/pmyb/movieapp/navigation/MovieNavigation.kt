package ec.com.pmyb.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ec.com.pmyb.movieapp.screens.details.DetailsScreen
import ec.com.pmyb.movieapp.screens.home.HomeScreen

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MovieScreens.HomeScreen.name
    ) {
        composable(MovieScreens.HomeScreen.name) {
            //here we pass where this should lead us to
            HomeScreen(navController = navController)
        }
        composable(
            MovieScreens.DetailsScreen.name + "/{movie}",
            arguments = listOf(navArgument(name = "movie") { type = NavType.StringType })
        ) {it->
            //here we pass where this should lead us to
            DetailsScreen(navController = navController, it.arguments?.getString("movie"))
        }
    }
}