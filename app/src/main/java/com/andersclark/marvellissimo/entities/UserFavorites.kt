package com.andersclark.marvellissimo.entities

data class UserFavorites(
    val userId: String,
    val favorites: List<MarvelEntity> = emptyList()
)