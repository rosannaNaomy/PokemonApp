package com.np.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.np.pokemonapp.R
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.ui.adapter.PokemonDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment @Inject constructor(
    private val pokemonDetailsAdapter: PokemonDetailsAdapter
) : Fragment(R.layout.fragment_pokemon_details) {

    //    private lateinit var viewModel: PokemonSharedViewModel
    private val viewModel by activityViewModels<PokemonSharedViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity())[PokemonSharedViewModel::class.java]

        setupRecyclerView()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.pokemonWithAbilities.observe(viewLifecycleOwner) {
            pokemonDetailsAdapter.updateList(it.abilities)
            setUpHeader(it)
        }
    }

    private fun setUpHeader(pokemonAbilitiesDomainModel: PokemonAbilitiesDomainModel) {
        pokemon_name_details_screen_tv.text = pokemonAbilitiesDomainModel.name
    }

    private fun setupRecyclerView() {
        recycler_pokemon_abilities.apply {
            adapter = pokemonDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


}