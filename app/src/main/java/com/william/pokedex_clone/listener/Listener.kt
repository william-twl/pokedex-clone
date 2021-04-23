package com.william.pokedex_clone.listener

interface OnClickListener<T> {

    fun onItemClicked(data: T, position: Int)
}