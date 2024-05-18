package com.jairorr.practicegym.domain

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    private val apiKey:String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization","Bearer $apiKey").build()
        return chain.proceed(request)
    }
}