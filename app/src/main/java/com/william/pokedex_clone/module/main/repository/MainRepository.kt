package com.william.pokedex_clone.module.main.repository

import com.william.pokedex_clone.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getPokemonList() = apiHelper.getPokemonList(100, 0)
}