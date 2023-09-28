package com.example.jetweatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.favorite.FavoriteViewModel

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    val showIt = remember {
        mutableStateOf(false)
    }
    val msm = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert, contentDescription = "More Icon",
                        tint = Color.White
                    )
                }
            } else Box {

            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if (isMainScreen) {
                val isAlreadyFavList = favoriteViewModel
                    .favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if (isAlreadyFavList.isEmpty()) {
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red.copy(alpha = 0.6f),
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val listData = title.split(",")
                                favoriteViewModel
                                    .insertFavorite(
                                        Favorite(
                                            city = listData[0],
                                            country = listData[1]
                                        )
                                    )
                                    .run {
                                        showIt.value = true
                                        msm.value = " Added to Favorites"
                                    }
                            })
                } else {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red.copy(alpha = 0.6f),
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val listData = title.split(",")
                                favoriteViewModel
                                    .deleteFavorite(
                                        Favorite(
                                            city = listData[0],
                                            country = listData[1]
                                        )
                                    )
                                    .run {
                                        showIt.value = true
                                        msm.value = " Delete of Favorites"
                                    }
                            })
                }

                ShowToast(context = context, showIt, msm.value)

            }
        },
//        backgroundColor = Color.Transparent,
        backgroundColor = Color(0xFF353636),
        elevation = elevation
    )

}


@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>, msm: String) {
    if (showIt.value) {
        Toast.makeText(
            context, msm,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color(0xFF353636))
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                    navegationOptionMenu(text = text, navController = navController)
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        }, contentDescription = null,
                        tint = when (text) {
                            "About" -> Color(0xFF0061a4)
                            "Favorites" -> Color.Red
                            else -> Color.Gray
                        }
                    )
                    Text(
                        text = text, modifier = Modifier
                            .clickable {
                                navegationOptionMenu(text = text, navController = navController)
                            }
                            .padding(start = 10.dp), fontWeight = FontWeight.W300,
                        color = Color.Black
                    )
                }
            }
        }
    }


}

private fun navegationOptionMenu(text: String, navController: NavController) {
    navController.navigate(
        when (text) {
            "About" -> WeatherScreens.AboutScreen.name
            "Favorites" -> WeatherScreens.FavoriteScreen.name
            else -> WeatherScreens.SettingsScreen.name
        }
    )
}
