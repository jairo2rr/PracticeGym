package com.jairorr.practicegym.data.api

data class SearchResponse(
    val href:String,
    val limit:Int,
    val next:String,
    val offset:Int,
    val previous:String,
    val total:Int,
    val items:List<Artist>
)
