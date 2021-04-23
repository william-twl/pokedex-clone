package com.william.pokedex_clone.module.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.william.pokedex_clone.R
import com.william.pokedex_clone.api.ApiHelper
import com.william.pokedex_clone.api.RetrofitBuilder
import com.william.pokedex_clone.databinding.ActivityMainBinding
import com.william.pokedex_clone.enum.Status
import com.william.pokedex_clone.listener.OnClickListener
import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.module.main.adapter.PokemonListAdapter
import com.william.pokedex_clone.module.main.viewmodel.MainViewModel
import com.william.pokedex_clone.module.main.viewmodel.MainViewModelFactory
import com.william.pokedex_clone.module.pokemon_detail.activity.PokemonDetailActivity
import com.william.pokedex_clone.utils.GlobalIntent
import com.william.pokedex_clone.utils.toCapitalise

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var mainBinding: ActivityMainBinding
    lateinit var adapter: PokemonListAdapter

    var pokemonList = ArrayList<GeneralObject?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.pokemon_list)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViewModel()
        setupRecyclerView()
        setupRefreshLayout()
        getPokemonList()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupRecyclerView() {
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonListAdapter(pokemonList, object : OnClickListener<GeneralObject?> {
            override fun onItemClicked(data: GeneralObject?, position: Int) {
                val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                intent.putExtra(GlobalIntent.INTENT_GENERAL_OBJECT, data)
                startActivity(intent)
            }
        })
        mainBinding.recyclerView.adapter = adapter
        mainBinding.recyclerView
    }

    private fun setupRefreshLayout() {
        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            getPokemonList()
        }
    }

    private fun getPokemonList() {
        viewModel.getPokemonList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        toggleProgress(false)
                        toggleError(false)
                        updateList(resource.data?.results.toCapitalise())
                    }

                    Status.ERROR -> {
                        toggleProgress(false)
                        toggleError(true)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }

                    Status.LOADING -> {
                        toggleProgress(true)
                        toggleError(false)
                    }
                }
            }
        })
    }

//    Show Progress When Waiting For Data Return / Hide Progress When Receive Data
    private fun toggleProgress(status: Boolean) {
        mainBinding.loading.visibility = if (status) View.VISIBLE else View.GONE
        mainBinding.swipeRefreshLayout.isRefreshing = false
    }

//    Show Error Layout When No Network
    private fun toggleError(status: Boolean) {
        mainBinding.errorLayout.visibility = if (status) View.VISIBLE else View.GONE
        mainBinding.swipeRefreshLayout.isRefreshing = false
    }

//    Update Current List With Returned Data
    private fun updateList(data: ArrayList<GeneralObject?>?) {
        pokemonList.clear()
        data?.let {
            pokemonList.addAll(it)
        }
        adapter.notifyDataSetChanged()
    }
}