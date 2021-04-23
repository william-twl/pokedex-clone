package com.william.pokedex_clone.module.pokemon_detail.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.william.pokedex_clone.R
import com.william.pokedex_clone.api.ApiHelper
import com.william.pokedex_clone.api.RetrofitBuilder
import com.william.pokedex_clone.base.BaseViewPagerAdapter
import com.william.pokedex_clone.databinding.ActivityPokemonDetailBinding
import com.william.pokedex_clone.enum.Status
import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.model.PokemonCover
import com.william.pokedex_clone.module.pokemon_detail.fragment.PokemonAbilityFragment
import com.william.pokedex_clone.module.pokemon_detail.viewmodel.PokemonDetailViewModel
import com.william.pokedex_clone.module.pokemon_detail.viewmodel.PokemonDetailViewModelFactory
import com.william.pokedex_clone.utils.GlobalIntent
import com.william.pokedex_clone.utils.returnId
import java.util.*

class PokemonDetailActivity : AppCompatActivity() {
    var data: GeneralObject? = null

    lateinit var viewModel: PokemonDetailViewModel
    lateinit var detailBinding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        supportActionBar?.title = getString(R.string.pokemon_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        data = intent.getSerializableExtra(GlobalIntent.INTENT_GENERAL_OBJECT) as? GeneralObject

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_detail)

        setupViewModel()
        setupRefreshLayout()
        getPokemonDetail()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            PokemonDetailViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(PokemonDetailViewModel::class.java)
    }

    private fun setupRefreshLayout() {
        detailBinding.swipeRefreshLayout.setOnRefreshListener {
            getPokemonDetail()
        }
    }

    private fun getPokemonDetail() {
        viewModel.getPokemon(data?.url?.returnId()).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        toggleProgress(false)
                        toggleError(false)
                        updateUI(it.data)
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

    private fun updateUI(data: PokemonCover?) {
        supportActionBar?.title = data?.name?.capitalize(Locale.getDefault())?:getString(R.string.pokemon_detail)
        val uri = Uri.parse(data?.sprites?.frontDefault?:"")
        detailBinding.icon.setImageURI(uri)

        if (data?.abilities != null) {
            val abilityViewPagerAdapter = BaseViewPagerAdapter(supportFragmentManager, this)
            data.abilities?.forEachIndexed { index, abilityObject ->
                abilityViewPagerAdapter.addFragment(
                    PokemonAbilityFragment.newInstance(abilityObject?.ability?.url?:""),
                    getString(R.string.ability_tab_text, index.plus(1).toString()))
            }
            detailBinding.abilityViewPager.adapter = abilityViewPagerAdapter
            detailBinding.abilityViewPager.offscreenPageLimit = data.abilities?.size?:1
            detailBinding.abilityTabLayout.setupWithViewPager(detailBinding.abilityViewPager)
        }

    }

//    Show Progress When Waiting For Data Return / Hide Progress When Receive Data
    private fun toggleProgress(status: Boolean) {
        detailBinding.loading.visibility = if (status) View.VISIBLE else View.GONE
        detailBinding.swipeRefreshLayout.isRefreshing = false
    }

//    Show Error Layout When No Network
    private fun toggleError(status: Boolean) {
        detailBinding.errorLayout.visibility = if (status) View.VISIBLE else View.GONE
        detailBinding.swipeRefreshLayout.isRefreshing = false
    }
}