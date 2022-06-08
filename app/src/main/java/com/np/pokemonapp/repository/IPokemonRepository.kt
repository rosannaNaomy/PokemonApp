package com.np.pokemonapp.repository

import androidx.lifecycle.LiveData
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.domain.model.PokemonDomainModel
import com.np.pokemonapp.util.Resource

interface IPokemonRepository {

    suspend fun isDataStored(): Boolean

    suspend fun isDataStored(id: Int): Boolean

    suspend fun getPokemonList(): Resource<PokemonResponse>

    suspend fun getPokemonInfo(id: Int): Resource<Pokemon>

    suspend fun getPokemonWithAbilities(id: Int): PokemonAbilitiesDomainModel

    fun allPokemonEntriesFromDB(): LiveData<List<PokemonDomainModel>>

    suspend fun checkDBForAbilitiesId(id: Int): List<PokemonAbility>

    suspend fun retrieveAllPokemonEntries(): List<PokemonEntry>

}