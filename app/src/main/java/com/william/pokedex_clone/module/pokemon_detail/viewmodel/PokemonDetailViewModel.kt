package com.william.pokedex_clone.module.pokemon_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.william.pokedex_clone.api.Resource
import com.william.pokedex_clone.module.pokemon_detail.repository.PokemonDetailRepository
import kotlinx.coroutines.Dispatchers

class PokemonDetailViewModel(private val pokemonDetailRepository: PokemonDetailRepository): ViewModel() {
    fun getPokemon(id: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = pokemonDetailRepository.getPokemon(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPokemonAbilityDetail(id: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = pokemonDetailRepository.getPokemonAbilityDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPokemonMoveDetail(id: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = pokemonDetailRepository.getPokemonMoveDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}