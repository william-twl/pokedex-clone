package com.william.pokedex_clone.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    var name: String?,
    @SerializedName("url")
    var pokemonUrl: String?
)

data class PokemonListCover(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: ArrayList<Pokemon?>?
)
