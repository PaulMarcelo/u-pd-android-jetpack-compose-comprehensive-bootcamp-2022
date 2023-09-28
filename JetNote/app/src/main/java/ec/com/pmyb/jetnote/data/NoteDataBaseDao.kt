package ec.com.pmyb.jetnote.data

import androidx.room.*
import ec.com.pmyb.jetnote.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDataBaseDao {
    @Query("SELECT * FROM notes_tbl")
    fun getNotes():Flow<List<Note>>
    @Query("SELECT * FROM notes_tbl WHERE id = :id")
    suspend fun getNotesById(id: String):Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)
    @Query("DELETE FROM notes_tbl")
    suspend fun deleteAll()
    @Delete
    suspend fun deleteNote(note: Note)

}