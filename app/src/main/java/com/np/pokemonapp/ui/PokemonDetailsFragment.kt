package com.np.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.np.pokemonapp.R
import com.np.pokemonapp.datasource.local.entities.PokemonWithAbilities
import com.np.pokemonapp.ui.adapter.PokemonDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment @Inject constructor(
    private val pokemonDetailsAdapter: PokemonDetailsAdapter
) : Fragment(R.layout.fragment_pokemon_details) {

    private lateinit var viewModel: PokemonSharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PokemonSharedViewModel::class.java]

        setupRecyclerView()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.pokemonWithAbilitiesList.observe(viewLifecycleOwner){
            pokemonDetailsAdapter.updateList(it.pokemonAbilities)
            setUpHeader(it)
        }
    }

    private fun setUpHeader(pokemonWithAbilities: PokemonWithAbilities){
        pokemon_name_details_screen_tv.text = pokemonWithAbilities.pokemon.pokemonName
    }

    private fun setupRecyclerView() {
        recycler_pokemon_abilities.apply {
            adapter = pokemonDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


}