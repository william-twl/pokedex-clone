package com.william.pokedex_clone.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPokemonList(limit: Int?, offset:Int?) = apiService.getPokemonList(limit, offset)

    suspend fun getPokemon(id: String?) = apiService.getPokemon(id)

    suspend fun getPokemonMoveDetail(id: String?) = apiService.getPokemonMoveDetail(id)

    suspend fun getPokemonAbilityDetail(id: String?) = apiService.getPokemonAbilityDetail(id)
}