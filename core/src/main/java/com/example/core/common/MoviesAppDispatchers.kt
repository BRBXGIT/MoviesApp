package com.example.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val moviesAppDispatcher: MoviesAppDispatchers)

enum class MoviesAppDispatchers {
    Default,
    Io
}