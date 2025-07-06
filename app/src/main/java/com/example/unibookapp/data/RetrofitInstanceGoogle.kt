package com.example.unibookapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceGoogle {
    private const val BASE_URL = "https://www.googleapis.com/"

    // Lazy creates the API service instance for google books api calls, using Gson for JSON parsing
    val api: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}
