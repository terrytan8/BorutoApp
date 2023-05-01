package com.example.borutoapp.domain.use_case.save_onboarding

import com.example.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke (completed: Boolean){
        repository.saveOnBoardingState(completed = completed)
    }
}