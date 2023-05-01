package com.example.borutoapp.di

import android.content.Context
import com.example.borutoapp.data.repository.DataStoreOperationImpl
import com.example.borutoapp.data.repository.Repository
import com.example.borutoapp.domain.repository.DataStoreOperations
import com.example.borutoapp.domain.use_case.UseCases
import com.example.borutoapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import com.example.borutoapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase
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

    @Provides
    @Singleton
    fun provideUseCases(
        repository: Repository
    ):UseCases{
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository)
        )
    }
}