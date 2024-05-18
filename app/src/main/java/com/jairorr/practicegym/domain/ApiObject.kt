package com.jairorr.practicegym.domain

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiObject {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/v1")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()
    var service: ApiService = retrofit.create(ApiService::class.java)

    private fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .build()
}