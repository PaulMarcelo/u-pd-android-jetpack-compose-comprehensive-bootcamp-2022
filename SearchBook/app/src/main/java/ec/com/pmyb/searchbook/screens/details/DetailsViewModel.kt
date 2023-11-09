package ec.com.pmyb.searchbook.screens.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.pmyb.searchbook.data.Resource
import ec.com.pmyb.searchbook.model.Item
import ec.com.pmyb.searchbook.repository.BookRepository
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository):ViewModel() {

    suspend fun getBookInfo(bookId: String): Resource<Item>{
        return repository.getBookInfo(bookId)
    }
}