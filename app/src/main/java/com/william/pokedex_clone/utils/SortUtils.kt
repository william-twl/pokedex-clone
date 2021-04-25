package com.william.pokedex_clone.utils

import com.william.pokedex_clone.model.GeneralObject
import kotlin.collections.ArrayList

// Assign an id for each General Object within General Object Array List
fun ArrayList<GeneralObject?>?.assignIds(): ArrayList<GeneralObject?> {
    val arrayList = arrayListOf<GeneralObject?>()
    this?.forEachIndexed { index, pokemon ->
        pokemon?.id = index
        arrayList.add(pokemon)
    }
    return arrayList
}

// Sort the General Object Array List by Id
fun ArrayList<GeneralObject?>?.sortById(): ArrayList<GeneralObject?> {
    return ArrayList(this?.sortedWith(compareBy {it?.id}))
}

// Sort the General Object Array List by Name In Ascending Order
fun ArrayList<GeneralObject?>?.sortByNameAscending(): ArrayList<GeneralObject?> {
    return ArrayList(this?.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {it?.name?:""}))
}

// Sort the General Object Array List by Name In Descending Order
fun ArrayList<GeneralObject?>?.sortByNameDescending(): ArrayList<GeneralObject?> {
    return ArrayList(this?.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) {it?.name?:""}))
}



