package ec.com.pmyb.jetnote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ec.com.pmyb.jetnote.model.Note
import ec.com.pmyb.jetnote.util.DateConverter
import ec.com.pmyb.jetnote.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDataBase: RoomDatabase() {
    abstract fun noteDato(): NoteDataBaseDao
}