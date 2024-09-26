package com.example.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [TMDBUser::class]
)
abstract class UserDb: RoomDatabase() {

    abstract fun TMDBUserDao(): TMDBUserDao
}