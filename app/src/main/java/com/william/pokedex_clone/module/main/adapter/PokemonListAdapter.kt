package com.william.pokedex_clone.module.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.william.pokedex_clone.listener.OnClickListener
import com.william.pokedex_clone.listener.OnFavouriteClickListener
import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.module.main.holder.PokemonListViewHolder

class PokemonListAdapter(val data: ArrayList<GeneralObject?>?,
                         var favouriteList: ArrayList<GeneralObject?>?,
                         var listener: OnClickListener<GeneralObject?>,
                         var favouriteListener: OnFavouriteClickListener<GeneralObject?>): RecyclerView.Adapter<PokemonListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        return PokemonListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bindTo(data?.get(position), listener, favouriteListener, isFavourite(data?.get(position)))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    private fun isFavourite(data: GeneralObject?): Boolean {
        return favouriteList?.contains(data) == true
    }
}