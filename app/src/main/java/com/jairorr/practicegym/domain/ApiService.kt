package com.jairorr.practicegym.domain

import com.jairorr.practicegym.data.api.SearchResponse
import com.jairorr.practicegym.data.api.TypeSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("")
    suspend fun getList()

    @POST("")
    suspend fun postItem()

    @GET("/search")
    suspend fun getSearchResponse(@Query("q") search:String, @Query("type") type:TypeSearch, @Query("market") market:String = "ES", @Query("limit") limit:Int = 5, @Query("offset") offset:Int = 0):Response<SearchResponse>
}