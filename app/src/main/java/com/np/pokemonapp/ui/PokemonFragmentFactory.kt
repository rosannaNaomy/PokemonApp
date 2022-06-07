package com.np.pokemonapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.np.pokemonapp.ui.adapter.PokemonNamesListAdapter
import com.np.pokemonapp.ui.adapter.PokemonDetailsAdapter
import javax.inject.Inject

class PokemonFragmentFactory @Inject constructor(
    private val pokemonNamesListAdapter: PokemonNamesListAdapter,
    private val pokemonDetailsAdapter: PokemonDetailsAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PokemonListFragment::class.java.name -> PokemonListFragment(pokemonNamesListAdapter)
            PokemonDetailsFragment::class.java.name -> PokemonDetailsFragment(pokemonDetailsAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}