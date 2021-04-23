package com.william.pokedex_clone.utils

import android.webkit.URLUtil
import com.william.pokedex_clone.model.GeneralObject
import java.util.*
import kotlin.collections.ArrayList

//  Capitalise first letter of every name String within Pokemon Array List
fun ArrayList<GeneralObject?>?.toCapitalise(): ArrayList<GeneralObject?> {
    val arrayList = arrayListOf<GeneralObject?>()
    this?.forEach { pokemon ->
        pokemon?.name = pokemon?.name?.capitalize(Locale.getDefault())?:""
        arrayList.add(pokemon)
    }
    return arrayList
}


fun String.returnId(): String {
    return if (URLUtil.isValidUrl(this)) {
        val stringArray = this.split("/")
        if (stringArray.isEmpty())
            "0"
        else stringArray[stringArray.size - 2]
    } else "0"
}