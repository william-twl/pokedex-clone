package com.william.pokedex_clone.api

import com.william.pokedex_clone.model.PokemonListCover
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit")limit: Int?, @Query("offset")offset: Int?): PokemonListCover?
}