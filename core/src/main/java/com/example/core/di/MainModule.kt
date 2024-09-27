package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.TMDBUserDao
import com.example.core.data.local.UserDb
import com.example.core.data.remote.TMDBApiInstance
import com.example.core.data.repos.AuthRepoImpl
import com.example.core.data.repos.LatestMoviesScreenRepoImpl
import com.example.core.data.repos.MovieScreenRepoImpl
import com.example.core.data.repos.ProfileScreenRepoImpl
import com.example.core.data.repos.UserFavoritesRepoImpl
import com.example.core.domain.AuthRepo
import com.example.core.domain.LatestMoviesScreenRepo
import com.example.core.domain.MovieScreenRepo
import com.example.core.domain.ProfileScreenRepo
import com.example.core.domain.UserFavoritesScreenRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideUserDao(@ApplicationContext context: Context): TMDBUserDao {
        return Room.databaseBuilder(
            context = context,
            klass = UserDb::class.java,
            name = "UserDb"
        ).build().TMDBUserDao()
    }

    @Provides
    @Singleton
    fun provideLatestMoviesScreenRepo(apiInstance: TMDBApiInstance): LatestMoviesScreenRepo {
        return LatestMoviesScreenRepoImpl(apiInstance)
    }

    @Provides
    @Singleton
    fun provideMovieScreenRepo(apiInstance: TMDBApiInstance): MovieScreenRepo {
        return MovieScreenRepoImpl(apiInstance)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(apiInstance: TMDBApiInstance, tmdbUserDao: TMDBUserDao): AuthRepo {
        return AuthRepoImpl(apiInstance, tmdbUserDao)
    }

    @Provides
    @Singleton
    fun provideUserFavoritesScreenRepo(apiInstance: TMDBApiInstance, tmdbUserDao: TMDBUserDao): UserFavoritesScreenRepo {
        return UserFavoritesRepoImpl(apiInstance, tmdbUserDao)
    }

    @Provides
    @Singleton
    fun provideProfileScreenRepo(apiInstance: TMDBApiInstance, tmdbUserDao: TMDBUserDao): ProfileScreenRepo {
        return ProfileScreenRepoImpl(apiInstance, tmdbUserDao)
    }
}