package com.example.core.data.local

import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface TMDBUserDao {

    @Upsert
    suspend fun upsertUser(tmdbUser: TMDBUser)

    @Query("SELECT * FROM tmdbuser")
    fun getUser(): Flow<TMDBUser>
}