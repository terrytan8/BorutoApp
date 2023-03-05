package com.example.borutoapp.di

import android.content.Context
import com.example.borutoapp.data.pref.DataStoreOperationImpl
import com.example.borutoapp.domain.repository.DataStoreOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperation (
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationImpl (context)
    }
}