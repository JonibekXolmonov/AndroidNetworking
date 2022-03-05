package com.example.androidnetworking.networking.retrofit

import com.example.androidnetworking.networking.retrofit.services.PosterService
import com.example.androidnetworking.networking.volley.VolleyHttp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {

    val IS_TESTER = true
    val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
    val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER)
            return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val posterService: PosterService = retrofit.create(PosterService::class.java)
}