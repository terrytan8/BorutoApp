package com.example.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.borutoapp.domain.use_case.UseCases
import com.example.borutoapp.domain.use_case.get_all_heroes.GetAllHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: UseCases
) : ViewModel() {
    val getAllHeroes = useCase.getAllHeroesUseCase()
}