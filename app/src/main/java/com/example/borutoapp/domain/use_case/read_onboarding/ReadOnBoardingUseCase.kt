package com.example.borutoapp.domain.use_case.read_onboarding

import com.example.borutoapp.data.pref.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}