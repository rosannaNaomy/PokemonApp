package com.np.pokemonapp.repository

import android.util.Log
import com.np.pokemonapp.datasource.local.PokemonDao
import com.np.pokemonapp.datasource.network.PokeAPI
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.util.Constants.LIMIT
import com.np.pokemonapp.util.Constants.OFFSET
import com.np.pokemonapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokeAPI,
    private val pokemonDao: PokemonDao
) {

    suspend fun getPokemonList(): Resource<PokemonResponse> {
        val response = try {
            api.getPokemonList(LIMIT, OFFSET)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }
}