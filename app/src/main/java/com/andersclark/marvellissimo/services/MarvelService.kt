package com.andersclark.marvellissimo.services
import com.andersclark.marvellissimo.entities.MarvelCharacterWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {
        @GET("characters")
        fun getSomeCharacters(
            @Query("nameStartsWith") nameStartsWith: String? = null,
            @Query("name") byExactName: String? = null,
            @Query("orderBy") orderBy: String? = null,
            @Query("limit") limit: Int? = null,
            @Query("offset") offset: Int? = null
        ): Single<MarvelCharacterWrapper>
    }
