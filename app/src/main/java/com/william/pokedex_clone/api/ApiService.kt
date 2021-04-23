package com.william.pokedex_clone.api

import com.william.pokedex_clone.model.PokemonAbilityCover
import com.william.pokedex_clone.model.PokemonCover
import com.william.pokedex_clone.model.PokemonListCover
import com.william.pokedex_clone.model.PokemonMoveCover
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit")limit: Int?, @Query("offset")offset: Int?): PokemonListCover?

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: String?): PokemonCover?

    @GET("ability/{id}")
    suspend fun getPokemonAbilityDetail(@Path("id") id: String?): PokemonAbilityCover?
}