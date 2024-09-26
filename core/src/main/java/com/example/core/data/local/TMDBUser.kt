package com.example.core.data.local

import androidx.room.Entity

@Entity
data class TMDBUser(
    val sessionId: String,
    val userId: String
)