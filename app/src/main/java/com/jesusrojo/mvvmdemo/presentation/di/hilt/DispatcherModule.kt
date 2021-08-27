package com.jesusrojo.mvvmdemo.presentation.di.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

// INJECT DISPATCHER
// https://stackoverflow.com/questions/63847790/hilt-missing-binding-on-coroutinedispatcher
// https://www.valueof.io/blog/injecting-coroutines-dispatchers-with-dagger
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher
// END INJECT DISPATCHER