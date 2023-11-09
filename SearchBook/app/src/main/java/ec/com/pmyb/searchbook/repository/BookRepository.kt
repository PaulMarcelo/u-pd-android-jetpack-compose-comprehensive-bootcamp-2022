package ec.com.pmyb.searchbook.repository

import ec.com.pmyb.searchbook.data.DataorException
import ec.com.pmyb.searchbook.data.Resource
import ec.com.pmyb.searchbook.model.Item
import ec.com.pmyb.searchbook.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataorException = DataorException<List<Item>, Boolean, Exception>()
    private val bookInfoDataorException = DataorException<Item, Boolean, Exception>()
//    suspend fun getBooks(serachQuery: String): DataorException<List<Item>, Boolean, Exception> {
//        try {
//            dataorException.loading = true
//            dataorException.data = api.getAllBooks(serachQuery).items
//            if (dataorException.data!!.isNotEmpty())
//                dataorException.loading = false
//        } catch (e: Exception) {
//            dataorException.e = e
//        }
//        return dataorException
//    }

//    suspend fun getBookInfo(bookId: String): DataorException<Item, Boolean, Exception> {
//        try {
//            bookInfoDataorException.loading = true
//            bookInfoDataorException.data = api.getBookInfo(bookId)
//            if (bookInfoDataorException.data.toString().isNotEmpty())
//                bookInfoDataorException.loading = false
//        } catch (e: Exception) {
//            bookInfoDataorException.e = e
//        }
//        return bookInfoDataorException
//    }


    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {

        return try {
            Resource.Loading(data = true)

            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)

        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }

    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)

        } catch (exception: Exception) {
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }

}