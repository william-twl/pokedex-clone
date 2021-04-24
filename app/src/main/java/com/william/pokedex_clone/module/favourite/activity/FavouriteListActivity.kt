package com.william.pokedex_clone.module.favourite.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.william.pokedex_clone.MyApplication
import com.william.pokedex_clone.R
import com.william.pokedex_clone.databinding.ActivityFavouriteListBinding
import com.william.pokedex_clone.enum.EventBusAction
import com.william.pokedex_clone.event.ActionEvent
import com.william.pokedex_clone.listener.OnClickListener
import com.william.pokedex_clone.listener.OnFavouriteClickListener
import com.william.pokedex_clone.listener.OnLongClickListener
import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.module.main.adapter.PokemonListAdapter
import com.william.pokedex_clone.module.pokemon_detail.activity.PokemonDetailActivity
import com.william.pokedex_clone.utils.GlobalIntent
import org.greenrobot.eventbus.EventBus

class FavouriteListActivity : AppCompatActivity() {
    lateinit var favouriteBinding: ActivityFavouriteListBinding
    lateinit var adapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)

        supportActionBar?.title = getString(R.string.favourite_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favouriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_list)

        setupRecyclerView()

        updateUI()
    }

    private fun setupRecyclerView() {
        favouriteBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PokemonListAdapter(MyApplication.instance.favouriteList,
            MyApplication.instance.favouriteList,
            object : OnClickListener<GeneralObject?> {
            override fun onItemClicked(data: GeneralObject?, position: Int) {
                val intent = Intent(this@FavouriteListActivity, PokemonDetailActivity::class.java)
                intent.putExtra(GlobalIntent.INTENT_GENERAL_OBJECT, data)
                startActivity(intent)
            }
        }, object : OnFavouriteClickListener<GeneralObject?> {
            override fun onFavouriteClicked(data: GeneralObject?, position: Int) {
                updateFavouriteList(data)
            }

        })

        favouriteBinding.recyclerView.adapter = adapter
        favouriteBinding.recyclerView
    }

    private fun updateFavouriteList(data: GeneralObject?) {
        if (MyApplication.instance.favouriteList?.contains(data) == true) {
            MyApplication.instance.favouriteList?.remove(data)
            runToast(getString(R.string.removed_from_favourite, data?.name?:""))
        } else {
            MyApplication.instance.favouriteList?.add(data)
            runToast(getString(R.string.added_to_favourite, data?.name?:""))
        }
        postEvent()
        updateUI()
    }

    private fun postEvent() {
        EventBus.getDefault().post(ActionEvent(EventBusAction.UPDATE_MAIN_LIST))
    }

    private fun updateUI() {
        adapter.notifyDataSetChanged()
        toggleNoData(MyApplication.instance.favouriteList?.isEmpty() == true)
    }

//    Show No Data Layout When No Data
    private fun toggleNoData(state: Boolean) {
        favouriteBinding.noDataLayout.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
}