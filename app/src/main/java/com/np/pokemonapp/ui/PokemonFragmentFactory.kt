package com.np.pokemonapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.np.pokemonapp.ui.adapter.PokemonAdapter
import javax.inject.Inject

class PokemonFragmentFactory @Inject constructor(
    private val pokemonAdapter: PokemonAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PokemonListFragment::class.java.name -> PokemonListFragment(pokemonAdapter)
            PokemonDetailsFragment::class.java.name -> PokemonDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}