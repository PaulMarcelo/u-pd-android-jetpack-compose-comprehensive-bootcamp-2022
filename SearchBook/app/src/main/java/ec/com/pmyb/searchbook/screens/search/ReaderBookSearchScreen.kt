package ec.com.pmyb.searchbook.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import ec.com.pmyb.searchbook.components.InputField
import ec.com.pmyb.searchbook.components.ReaderAppBar
import ec.com.pmyb.searchbook.model.Item
import ec.com.pmyb.searchbook.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun SearcheScreen(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                showProfile = false
            ) {
//                navController.popBackStack()
                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
            }
        }
    ) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                { searchQuery ->
                    viewModel.isLoading = true
                    viewModel.searchBooks(query = searchQuery)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController)

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        InputField(valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}


@Composable
fun BookList(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {

//    val listOfBooks = listOf<MBook>(
//        MBook("1", "Libro 1", "autor 1", "nota 1"),
//        MBook("2", "Libro 2", "autor 2", "nota 2"),
//        MBook("3", "Libro 3", "autor 3", "nota 3")
//    )

    val listOfBooks = viewModel.list
    if (viewModel.isLoading) {
        Column(
            modifier = Modifier.padding(end = 2.dp).fillMaxWidth(),

//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator()
            Text(text = "Loading...")
        }

    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }
        }
    }

}

@Composable
fun BookRow(book: Item, navController: NavController) {

    Card(modifier = Modifier
        .clickable {
             navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 7.dp,
        backgroundColor = Color.White) {

        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl: String =
                book.volumeInfo.imageLinks.smallThumbnail.ifEmpty { "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80" }
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
            )

            Column {
                Text(
                    text = book.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,
                    color = Color.Black
                )
                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,
                    color = Color.Black

                )
                Text(
                    text = "${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,
                    color = Color.Black
                )
            }
        }

    }


}


