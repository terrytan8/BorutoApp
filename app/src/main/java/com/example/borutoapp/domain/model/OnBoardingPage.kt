package com.example.borutoapp.domain.model

import androidx.annotation.DrawableRes
import com.example.borutoapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.greetings,
        title = "Greetings",
        description = "Welcome to Boruto Collection"
    )
    object Second : OnBoardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "Explore every kind of Boruto character information"
    )
    object Third : OnBoardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Find your favourite heroes and learn some of the things you didn't know!"
    )
}
