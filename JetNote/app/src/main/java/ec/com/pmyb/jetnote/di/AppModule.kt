package ec.com.pmyb.jetnote.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ec.com.pmyb.jetnote.data.NoteDataBase
import ec.com.pmyb.jetnote.data.NoteDataBaseDao
import ec.com.pmyb.jetnote.model.Note
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    /**
     *
     */
    @Singleton
    @Provides
    fun provideNotesDao(noteDataBase: NoteDataBase): NoteDataBaseDao = noteDataBase.noteDato()
    /**
     *
     */
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): NoteDataBase =
        Room.databaseBuilder(context, NoteDataBase::class.java, "note_db")
            .fallbackToDestructiveMigration()
            .build()

}