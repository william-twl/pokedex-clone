package com.william.pokedex_clone.module.pokemon_detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.william.pokedex_clone.R
import com.william.pokedex_clone.api.ApiHelper
import com.william.pokedex_clone.api.RetrofitBuilder
import com.william.pokedex_clone.databinding.FragmentPokemonAbilityBinding
import com.william.pokedex_clone.enum.Status
import com.william.pokedex_clone.model.EffectEntry
import com.william.pokedex_clone.model.PokemonAbilityCover
import com.william.pokedex_clone.module.pokemon_detail.viewmodel.PokemonDetailViewModel
import com.william.pokedex_clone.module.pokemon_detail.viewmodel.PokemonDetailViewModelFactory
import com.william.pokedex_clone.utils.returnId
import java.util.*

private const val API_URL = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonAbilityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonAbilityFragment : Fragment() {
    private var url: String? = null
    lateinit var fragmentBinding: FragmentPokemonAbilityBinding
    lateinit var viewModel: PokemonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(API_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding = FragmentPokemonAbilityBinding.bind(view)

        setupViewModel()

        getPokemonAbilityDetail()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            PokemonDetailViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(PokemonDetailViewModel::class.java)
    }

    private fun getPokemonAbilityDetail() {
        viewModel.getPokemonAbilityDetail(url?.returnId()).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        toggleProgress(false)
                        updateUI(it.data)
                    }

                    Status.LOADING -> {
                        toggleProgress(true)
                    }

                    Status.ERROR -> {
                        toggleProgress(false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    private fun updateUI(data: PokemonAbilityCover?) {
        data?.name = data?.name?.capitalize(Locale.getDefault())?:""
        fragmentBinding.abilityObject = data
        var effect: EffectEntry? = null
        data?.effectEntries?.forEach { itEntry ->
            if (itEntry?.language?.name == "en") {
                effect = itEntry
                return@forEach
            }
        }
        fragmentBinding.effectObject = effect
    }

//    Show Progress When Waiting For Data Return / Hide Progress When Receive Data
    private fun toggleProgress(status: Boolean) {
        fragmentBinding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 String.
         * @return A new instance of fragment PokemonAbilityFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            PokemonAbilityFragment().apply {
                arguments = Bundle().apply {
                    putString(API_URL, param1)
                }
            }
    }
}