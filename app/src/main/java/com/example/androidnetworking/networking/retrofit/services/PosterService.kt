package com.example.androidnetworking.networking.retrofit.services

import com.example.androidnetworking.model.Poster
import com.example.androidnetworking.model.PosterResp
import retrofit2.Call
import retrofit2.http.*

interface PosterService {

    @Headers(
        "Content-type:application/json"
    )

    @GET("posts")
    fun listPost(): Call<ArrayList<PosterResp>>

    @GET("posts/{id}")
    fun singlePost(@Path("id") id: Int): Call<PosterResp>

    @POST("posts")
    fun createPost(@Body post: Poster): Call<PosterResp>

    @PUT("posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Poster): Call<PosterResp>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<PosterResp>

}