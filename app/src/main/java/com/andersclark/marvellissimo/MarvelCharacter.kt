package com.andersclark.marvellissimo
import java.io.Serializable

data class MarvelCharacter(
    val id: String,
    val name: String,
    val description: String,
    val resourceURI: String
): Serializable