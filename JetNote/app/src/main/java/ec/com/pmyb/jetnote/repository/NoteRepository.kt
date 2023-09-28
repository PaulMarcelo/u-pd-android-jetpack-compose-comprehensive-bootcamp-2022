package ec.com.pmyb.jetnote.repository

import ec.com.pmyb.jetnote.data.NoteDataBaseDao
import ec.com.pmyb.jetnote.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDataBaseDao: NoteDataBaseDao
) {

    suspend fun addNote(note: Note) = noteDataBaseDao.insert(note)
    suspend fun updateNote(note: Note) = noteDataBaseDao.update(note)
    suspend fun deleteNote(note: Note) = noteDataBaseDao.deleteNote(note)
    suspend fun deleteAll() = noteDataBaseDao.deleteAll()
    fun getAllNote(): Flow<List<Note>> =
        noteDataBaseDao.getNotes().flowOn(Dispatchers.IO).conflate()


}