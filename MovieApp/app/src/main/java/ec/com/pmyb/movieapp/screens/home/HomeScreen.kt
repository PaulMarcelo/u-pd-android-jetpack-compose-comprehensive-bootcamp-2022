package ec.com.pmyb.movieapp.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ec.com.pmyb.movieapp.MovieRow
import ec.com.pmyb.movieapp.navigation.MovieScreens

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Text(text = "Movies")
        }
    }) {
        MainContent(navController)
    }
}

@Composable
fun MainContent(
    navController: NavController, movieList: List<String> = listOf(
        "Avatar", "300", "Harry Potter", "Life", "Life"
    )
) {
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movieList) {
                MovieRow(it) { movie ->
                    navController.navigate(route = MovieScreens.DetailsScreen.name+"/$movie")
                }
            }
        }
    }
}