package com.william.pokedex_clone.module.main.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.william.pokedex_clone.R
import com.william.pokedex_clone.databinding.ItemPokemonListBinding
import com.william.pokedex_clone.listener.OnClickListener
import com.william.pokedex_clone.model.GeneralObject
import kotlinx.android.extensions.LayoutContainer

class PokemonListViewHolder(override val containerView: View?, val binding: ItemPokemonListBinding) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {

    fun bindTo(data: GeneralObject?, listener: OnClickListener<GeneralObject?>) {
        binding.name.text = data?.name?:""
        containerView?.setOnClickListener {
            listener.onItemClicked(data, bindingAdapterPosition)
        }
    }

    companion object {

        fun create(parent: ViewGroup): PokemonListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pokemon_list, parent, false)
            val binding = ItemPokemonListBinding.bind(view)
            return PokemonListViewHolder(view, binding)
        }
    }
}