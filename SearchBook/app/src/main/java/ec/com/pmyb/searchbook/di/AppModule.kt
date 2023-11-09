package ec.com.pmyb.searchbook.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ec.com.pmyb.searchbook.network.BooksApi
import ec.com.pmyb.searchbook.repository.BookRepository
import ec.com.pmyb.searchbook.repository.FireRepository
import ec.com.pmyb.searchbook.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideFireBookRepository()
            = FireRepository(queryBook = FirebaseFirestore.getInstance()
        .collection("books"))

    @Singleton
    @Provides
    fun providerBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

}