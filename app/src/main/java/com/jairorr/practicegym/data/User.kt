package com.jairorr.practicegym.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid:Int=0,
    val firstName: String?,
    val lastName:String?
)
