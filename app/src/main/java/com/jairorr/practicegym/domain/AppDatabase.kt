package com.jairorr.practicegym.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jairorr.practicegym.data.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}