package com.np.pokemonapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.np.pokemonapp.datasource.local.PokemonDao
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonWithAbilities
import com.np.pokemonapp.datasource.network.PokeAPI
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.domain.mapper.DaoModelToDomainModel
import com.np.pokemonapp.domain.mapper.PokeResponseToDaoModel
import com.np.pokemonapp.domain.model.PokemonDomainModel
import com.np.pokemonapp.util.Constants.LIMIT
import com.np.pokemonapp.util.Constants.OFFSET
import com.np.pokemonapp.util.Resource
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokeAPI,
    private val pokemonDao: PokemonDao
) {

    suspend fun getPokemonList(): Resource<PokemonResponse> {
        pokemonDao.deleteAll()
        val response = try {
            api.getPokemonList(LIMIT, OFFSET)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        val list = PokeResponseToDaoModel.fromResponseList(response)
        list.map { pokemonDao.insertPokemon(it)}
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        val list = PokeResponseToDaoModel.fromSinglePokemonResponse(response)
        list.map { pokemonDao.insertPokemonAbility(it) }

        return Resource.Success(response)
    }

    suspend fun getPokemonWithAbilities(id: Int): PokemonWithAbilities{
        return pokemonDao.getPokemonWithAbilities(id)
    }

    fun allPokemonEntriesFromDB(): LiveData<List<PokemonDomainModel>> {
       return DaoModelToDomainModel.fromDatabaseList(pokemonDao.observeAllPokemonEntries())
    }

}