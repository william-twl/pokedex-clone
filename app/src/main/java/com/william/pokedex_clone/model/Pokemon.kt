package com.william.pokedex_clone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GeneralObject(
    var name: String?,
    var url: String?,
    var id: Int? = 0
): Serializable

data class PokemonListCover(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: ArrayList<GeneralObject?>?
): Serializable

data class PokemonCover(
    var abilities: ArrayList<AbilityObject?>?,
    var sprites: SpriteObject?,
    var name: String?
): Serializable

data class AbilityObject(
    var ability: GeneralObject?
): Serializable

data class SpriteObject(
    @SerializedName("back_default")
    var backDefault: String?,
    @SerializedName("front_default")
    var frontDefault: String?
): Serializable

data class PokemonAbilityCover(
    @SerializedName("effect_entries")
    var effectEntries: ArrayList<EffectEntry?>?,
    var name: String?
): Serializable

data class EffectEntry(
    @SerializedName("effect")
    var effectString: String?,
    var language: LanguageObject?
): Serializable

data class LanguageObject(
    var name: String?
): Serializable

data class PokemonMoveCover(
    @SerializedName("effect_entries")
    var effectEntries: ArrayList<EffectEntry?>?,
    var name: String?,
    var type: AttackTypeObject?
): Serializable

data class AttackTypeObject(
    var name: String?
): Serializable


