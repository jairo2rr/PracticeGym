package com.jairorr.practicegym.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jairorr.practicegym.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUsers(vararg users:User)

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun getUserById(uid: Int):Flow<User?>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")

}