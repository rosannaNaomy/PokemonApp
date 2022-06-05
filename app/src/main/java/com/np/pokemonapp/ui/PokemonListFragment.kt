package com.np.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.np.pokemonapp.R
import com.np.pokemonapp.ui.adapter.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import javax.inject.Inject


@AndroidEntryPoint
class PokemonListFragment @Inject constructor(
    val pokemonAdapter: PokemonAdapter
) : Fragment(R.layout.fragment_pokemon_list) {

    private lateinit var viewModel: PokemonSharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PokemonSharedViewModel::class.java]
        setupRecyclerView()
        subscribeToObservers()

        pokemonAdapter.setOnItemClickListener {
            findNavController().navigate(
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner) {
            pokemonAdapter.updateList(it)
        }
    }

    private fun setupRecyclerView() {
        recycler_pokemon_list.apply {
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}