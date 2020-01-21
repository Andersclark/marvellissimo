package com.andersclark.marvellissimo.entities

data class User(
    val uid: String,
    val username: String,
    val favorites: UserFavorites = UserFavorites(uid)
)