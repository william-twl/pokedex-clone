package com.william.pokedex_clone.listener

interface OnClickListener<T> {
    fun onItemClicked(data: T, position: Int)
}

interface OnLongClickListener<T> {
    fun onItemLongClicked(data: T, position: Int)
}

interface OnFavouriteClickListener<T> {
    fun onFavouriteClicked(data: T, position: Int)
}