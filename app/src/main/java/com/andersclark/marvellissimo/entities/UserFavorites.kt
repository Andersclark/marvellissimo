package com.andersclark.marvellissimo.entities

data class UserFavorites(
    val userId: String,
    val favorites: MutableList<MarvelEntity> = mutableListOf<MarvelEntity>()
)