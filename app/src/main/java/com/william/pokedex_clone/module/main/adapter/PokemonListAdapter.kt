package com.william.pokedex_clone.module.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.william.pokedex_clone.module.main.holder.PokemonListViewHolder

class PokemonListAdapter(val data: ArrayList<String?>?): RecyclerView.Adapter<PokemonListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        return PokemonListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bindTo(data?.get(position))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }
}