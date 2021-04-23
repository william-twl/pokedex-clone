package com.william.pokedex_clone.module.pokemon_detail.repository

import com.william.pokedex_clone.api.ApiHelper

class PokemonDetailRepository(private val apiHelper: ApiHelper) {
    suspend fun getPokemon(id: String?) = apiHelper.getPokemon(id)

    suspend fun getPokemonMoveDetail(id: String?) = apiHelper.getPokemonMoveDetail(id)

    suspend fun getPokemonAbilityDetail(id: String?) = apiHelper.getPokemonAbilityDetail(id)
}