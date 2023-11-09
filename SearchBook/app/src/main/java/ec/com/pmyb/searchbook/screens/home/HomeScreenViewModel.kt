package ec.com.pmyb.searchbook.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.pmyb.searchbook.data.DataorException
import ec.com.pmyb.searchbook.model.MBook
import ec.com.pmyb.searchbook.repository.FireRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
) : ViewModel() {
    val data: MutableState<DataorException<List<MBook>, Boolean, Exception>> =
        mutableStateOf(DataorException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("GET", "getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")

    }


}