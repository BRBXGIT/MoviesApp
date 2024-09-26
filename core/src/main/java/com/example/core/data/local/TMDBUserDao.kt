package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TMDBUserDao {

    @Upsert
    suspend fun upsertUser(tmdbUser: TMDBUser)

    @Query("SELECT * FROM tmdbuser")
    fun getUser(): Flow<TMDBUser>
}