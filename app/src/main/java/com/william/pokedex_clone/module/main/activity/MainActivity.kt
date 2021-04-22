package com.william.pokedex_clone.module.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.william.pokedex_clone.R
import com.william.pokedex_clone.databinding.ActivityMainBinding
import com.william.pokedex_clone.module.main.adapter.PokemonListAdapter

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    lateinit var adapter: PokemonListAdapter

    var pokemonList = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {

    }

    private fun setupRecyclerView() {
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonListAdapter(pokemonList)
        mainBinding.recyclerView.adapter = adapter

        toggleProgress(false)
        val tempData = ArrayList<String?>()
        tempData.add("Pikachu")
        tempData.add("Charmender")
        tempData.add("Zizagoon")
        updateList(tempData)
    }

//    Show Progress When Waiting For Data Return / Hide Progress When Receive Data
    private fun toggleProgress(status: Boolean) {
        mainBinding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

//    Update Current List With Returned Data
    private fun updateList(data: ArrayList<String?>?) {
        pokemonList.clear()
        data?.let {
            pokemonList.addAll(it)
        }
        adapter.notifyDataSetChanged()
    }
}