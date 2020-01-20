package com.andersclark.marvellissimo.entities

data class UserFavorites(
    val user: User,
    val favorites: List<MarvelEntity>
)