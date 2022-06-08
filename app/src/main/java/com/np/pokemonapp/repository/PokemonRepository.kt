package com.np.pokemonapp.repository

import androidx.lifecycle.LiveData
import com.np.pokemonapp.datasource.local.PokemonDao
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.datasource.network.PokeAPI
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.domain.mapper.DaoModelToDomainModel
import com.np.pokemonapp.domain.mapper.PokeResponseToDaoModel
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.domain.model.PokemonDomainModel
import com.np.pokemonapp.util.Constants.LIMIT
import com.np.pokemonapp.util.Constants.OFFSET
import com.np.pokemonapp.util.Resource
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokeAPI,
    private val pokemonDao: PokemonDao
) : IPokemonRepository {

    override suspend fun isDataStored(): Boolean {
        return retrieveAllPokemonEntries().isNotEmpty()

    }

    override suspend fun isDataStored(id:Int): Boolean {
        return checkDBForAbilitiesId(id).isNotEmpty()

    }

    override suspend fun getPokemonList(): Resource<PokemonResponse> {
        val response = try {
            api.getPokemonList(LIMIT, OFFSET)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        val list = PokeResponseToDaoModel.fromResponseList(response)
        list.map { pokemonDao.insertPokemon(it) }
        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(id: Int): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }

        val list = PokeResponseToDaoModel.fromSinglePokemonResponse(response)
        list.map { pokemonDao.insertPokemonAbility(it) }
        return Resource.Success(response)
    }

    override suspend fun getPokemonWithAbilities(id: Int): PokemonAbilitiesDomainModel {
        return DaoModelToDomainModel.mapToPokeAbilityDomainModel(
            pokemonDao.getPokemonWithAbilities(
                id
            )
        )
    }

    override fun allPokemonEntriesFromDB(): LiveData<List<PokemonDomainModel>> {
        return DaoModelToDomainModel.fromDatabaseList(pokemonDao.observeAllPokemonEntries())
    }

    override suspend fun checkDBForAbilitiesId(id: Int): List<PokemonAbility> {
        return pokemonDao.getAbilities(id)
    }

    override suspend fun retrieveAllPokemonEntries(): List<PokemonEntry> {
        return pokemonDao.retrieveAllPokemonEntries()
    }

}