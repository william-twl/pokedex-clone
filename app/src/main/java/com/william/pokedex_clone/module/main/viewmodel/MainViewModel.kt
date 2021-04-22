package com.william.pokedex_clone.module.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.william.pokedex_clone.api.Resource
import com.william.pokedex_clone.module.main.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {
    fun getPokemonList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPokemonList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}