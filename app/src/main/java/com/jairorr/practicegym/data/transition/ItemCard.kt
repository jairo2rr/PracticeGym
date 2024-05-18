package com.jairorr.practicegym.data.transition

data class ItemCard(
    val subtitle:String,
    val question:String,
    val nQuestion:Int,
    val options:List<Options>
)

data class Options(
    val id:String,
    val description:String,
    val isCorrect:Boolean
)