package ec.com.pmyb.jetnote.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.pmyb.jetnote.data.NotesDataSource
import ec.com.pmyb.jetnote.model.Note
import ec.com.pmyb.jetnote.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNote().distinctUntilChanged()
                .collect { listOfNote ->
                    if (listOfNote.isNullOrEmpty()) {
                        Log.d("Empty", ": Empty list")
                    } else {
                        _noteList.value = listOfNote
                    }
                }
        }
    }

     fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }

     fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

     fun removeNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }


}