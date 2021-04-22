package com.william.pokedex_clone.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPokemonList(limit: Int?, offset:Int?) = apiService.getPokemonList(limit, offset)
}