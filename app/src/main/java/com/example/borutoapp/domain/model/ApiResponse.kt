package com.example.borutoapp.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
    val success:Boolean,
    val message:String? = null,
    val nextPage: Int? = null,
    val prevPage:Int? = null,
    val heroes : List<Hero> = emptyList()
)