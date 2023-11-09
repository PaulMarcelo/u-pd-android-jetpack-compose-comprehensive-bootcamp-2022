package ec.com.pmyb.searchbook.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import ec.com.pmyb.searchbook.data.DataorException
import ec.com.pmyb.searchbook.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireRepository @Inject constructor(
    private val queryBook: Query
) {
    suspend fun getAllBooksFromDatabase(): DataorException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataorException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data =  queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false


        }catch (exception: FirebaseFirestoreException){
            dataOrException.e = exception
        }
        return dataOrException

    }

}