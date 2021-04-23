package com.william.pokedex_clone.module.pokemon_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.william.pokedex_clone.api.ApiHelper
import com.william.pokedex_clone.module.pokemon_detail.repository.PokemonDetailRepository

class PokemonDetailViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(PokemonDetailRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}