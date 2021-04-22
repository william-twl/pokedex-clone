package com.william.pokedex_clone.utils

import android.webkit.URLUtil
import com.william.pokedex_clone.model.Pokemon
import java.util.*
import kotlin.collections.ArrayList

//  Capitalise first letter of every name String within Pokemon Array List
fun ArrayList<Pokemon?>?.toCapitalise(): ArrayList<Pokemon?> {
    val arrayList = arrayListOf<Pokemon?>()
    this?.forEach { pokemon ->
        pokemon?.name = pokemon?.name?.capitalize(Locale.getDefault())?:""
        arrayList.add(pokemon)
    }
    return arrayList
}