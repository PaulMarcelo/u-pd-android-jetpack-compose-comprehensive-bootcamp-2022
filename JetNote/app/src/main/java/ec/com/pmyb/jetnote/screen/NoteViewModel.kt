package ec.com.pmyb.jetnote.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ec.com.pmyb.jetnote.data.NotesDataSource
import ec.com.pmyb.jetnote.model.Note

class NoteViewModel: ViewModel() {
    private val _listNote= mutableStateListOf<Note>()

    init {
        this._listNote.addAll(NotesDataSource().loadNotes())
    }

    fun addNote(note: Note){
        this._listNote.add(note)
    }

    fun removeNote(note: Note){
        this._listNote.remove(note)
    }

    fun getAllNotes():List<Note>{
        return this._listNote
    }


}