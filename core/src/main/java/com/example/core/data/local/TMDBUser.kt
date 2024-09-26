package com.example.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TMDBUser(
    @PrimaryKey
    val sessionId: String,
    val userId: Int
)