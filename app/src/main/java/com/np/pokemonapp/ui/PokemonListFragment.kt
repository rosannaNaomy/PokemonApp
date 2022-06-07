package com.np.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.np.pokemonapp.R
import com.np.pokemonapp.ui.adapter.PokemonNamesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import javax.inject.Inject


@AndroidEntryPoint
class PokemonListFragment @Inject constructor(
    private val pokemonNamesListAdapter: PokemonNamesListAdapter
) : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel by activityViewModels<PokemonSharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToObservers()

        pokemonNamesListAdapter.setOnItemClickListener {
            viewModel.fetchSinglePokemonFromDB(it)
            findNavController().navigate(
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner) {
            pokemonNamesListAdapter.updateList(it)
        }
    }

    private fun setupRecyclerView() {
        recycler_pokemon_list.apply {
            adapter = pokemonNamesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}