package com.example.unibookapp.data

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface BookApiService {
    // Endpoint for google books api
    // "books/v1/volumes" is the path to search and combines with the base url from RetrofitInstanceGoogle
    @GET("books/v1/volumes")
    suspend fun searchBooks(@Query("q") query: String): Response<BookSearchResponse>
}

data class BookSearchResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String? = null,
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    val thumbnail: String? = null
)
