package com.william.pokedex_clone.module.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.william.pokedex_clone.MyApplication
import com.william.pokedex_clone.R
import com.william.pokedex_clone.api.ApiHelper
import com.william.pokedex_clone.api.RetrofitBuilder
import com.william.pokedex_clone.base.BaseDropDownAdapter
import com.william.pokedex_clone.databinding.ActivityMainBinding
import com.william.pokedex_clone.enum.EventBusAction
import com.william.pokedex_clone.enum.SortEnum
import com.william.pokedex_clone.enum.Status
import com.william.pokedex_clone.event.ActionEvent
import com.william.pokedex_clone.listener.OnClickListener
import com.william.pokedex_clone.listener.OnFavouriteClickListener
import com.william.pokedex_clone.listener.OnLongClickListener
import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.module.favourite.activity.FavouriteListActivity
import com.william.pokedex_clone.module.main.adapter.PokemonListAdapter
import com.william.pokedex_clone.module.main.viewmodel.MainViewModel
import com.william.pokedex_clone.module.main.viewmodel.MainViewModelFactory
import com.william.pokedex_clone.module.pokemon_detail.activity.PokemonDetailActivity
import com.william.pokedex_clone.utils.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var mainBinding: ActivityMainBinding
    lateinit var adapter: PokemonListAdapter

    var pokemonList = ArrayList<GeneralObject?>()

    private var sortEnum: SortEnum = SortEnum.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.pokemon_list)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViewModel()
        setupRecyclerView()
        setupRefreshLayout()
        setupSortListener()
        setupSortSpinner()
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
        adapter = PokemonListAdapter(pokemonList,
            MyApplication.instance.favouriteList,
            object : OnClickListener<GeneralObject?> {
            override fun onItemClicked(data: GeneralObject?, position: Int) {
                val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                intent.putExtra(GlobalIntent.INTENT_GENERAL_OBJECT, data)
                startActivity(intent)
            }
        }, object : OnFavouriteClickListener<GeneralObject?> {
            override fun onFavouriteClicked(data: GeneralObject?, position: Int) {
                updateFavouriteList(data)
            }

        })
        mainBinding.recyclerView.adapter = adapter
        mainBinding.recyclerView
    }

    private fun updateFavouriteList(data: GeneralObject?) {
        if (MyApplication.instance.favouriteList?.contains(data) == true) {
            MyApplication.instance.favouriteList?.remove(data)
            runToast(getString(R.string.removed_from_favourite, data?.name?:""))
        } else {
            MyApplication.instance.favouriteList?.add(data)
            runToast(getString(R.string.added_to_favourite, data?.name?:""))
        }

        adapter.notifyDataSetChanged()
    }

    private fun setupRefreshLayout() {
        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            getPokemonList()
        }
    }

    private fun setupSortListener() {
        mainBinding.sortText.setOnClickListener {
            mainBinding.sortSpinner.performClick()
        }
    }

    private fun setupSortSpinner() {
        val arrayString = resources.getStringArray(R.array.sort_array).asList()
        val walletToDataAdapter = BaseDropDownAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayString
        )

        mainBinding.sortSpinner.adapter = walletToDataAdapter
        mainBinding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        sortEnum = SortEnum.DEFAULT
                    }

                    1 -> {
                        sortEnum = SortEnum.NAME_ASC
                    }

                    2 -> {
                        sortEnum = SortEnum.NAME_DESC
                    }

                    else -> {
                        sortEnum = SortEnum.DEFAULT
                    }
                }
                mainBinding.sortText.text = arrayString[position]
                val arrayList = sortArrayList(pokemonList)
                updateList(arrayList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toFavourite -> {
                startActivity(Intent(this@MainActivity, FavouriteListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPokemonList() {
        viewModel.getPokemonList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        toggleProgress(false)
                        toggleError(false)
                        val arrayList = sortArrayList(resource.data?.results.toCapitalise().assignIds())
                        updateList(arrayList)
                    }

                    Status.ERROR -> {
                        toggleProgress(false)
                        toggleError(true)
                        runToast(it.message)
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

    private fun sortArrayList(data: ArrayList<GeneralObject?>?):  ArrayList<GeneralObject?>?{
        return when(sortEnum) {
            SortEnum.DEFAULT -> {
                data.sortById()
            }
            SortEnum.NAME_ASC -> {
                data.sortByNameAscending()
            }
            SortEnum.NAME_DESC -> {
                data.sortByNameDescending()
            }
            else -> {
                data.sortById()
            }
        }
    }

//    Update Current List With Returned Data
    private fun updateList(data: ArrayList<GeneralObject?>?) {
        pokemonList.clear()
        data?.let {
            pokemonList.addAll(it)
        }
        adapter.notifyDataSetChanged()
    }

//    General Function To Toast Message
    private fun runToast(message: String?) {
        Toast.makeText(this, message?:"", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        try {
            super.onStart()
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        } catch (e: Exception) {
        }

    }

    override fun onDestroy() {
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
            super.onDestroy()
        } catch (e: Exception) {
        }

    }

    @Subscribe
    fun onEvent(event: ActionEvent?) {
        if (event == null) return
        when (event.action) {
            EventBusAction.UPDATE_MAIN_LIST -> {
                adapter.notifyDataSetChanged()
            }
        }

    }
}