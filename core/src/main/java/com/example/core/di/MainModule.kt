package com.example.core.di

import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.repos.MoviesScreenRepoImpl
import com.example.core.domain.MoviesScreenRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesTMDBApiInstance(): TMDBApiInstance {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMoviesScreenRepo(apiInstance: TMDBApiInstance): MoviesScreenRepo {
        return MoviesScreenRepoImpl(apiInstance)
    }
}