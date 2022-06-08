package com.np.pokemonapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.datasource.local.entities.PokemonWithAbilities
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.datasource.network.response.Sprites
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.domain.model.PokemonDomainModel
import com.np.pokemonapp.util.Resource


class FakePokemonRepository : IPokemonRepository {

    private val pokemonItems = mutableListOf<PokemonDomainModel>()
    private lateinit var pokemonWithAbilities: PokemonAbilitiesDomainModel

    private val abilities = mutableListOf<PokemonAbility>()
    private var checkPokemonEntriesList = mutableListOf<PokemonEntry>()

    private val observablePokemonItems = MutableLiveData<List<PokemonDomainModel>>(pokemonItems)
    private val observablePokemonWithAbilities = MutableLiveData<PokemonAbilitiesDomainModel>()
    private val observableAbilities = MutableLiveData<List<PokemonAbility>>(abilities)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observablePokemonItems.postValue(pokemonItems)
        observablePokemonWithAbilities.postValue(pokemonWithAbilities)
    }

    override suspend fun isDataStored(): Boolean {
        return pokemonItems.isEmpty()
    }

    override suspend fun isDataStored(id: Int): Boolean {
        return abilities.isEmpty()
    }

    override suspend fun getPokemonList(): Resource<PokemonResponse> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            pokemonItems.add(PokemonDomainModel("Bulbasaur", 1))
            checkPokemonEntriesList.add(PokemonEntry(1, "Bulbasaur", "url"))

            pokemonWithAbilities = PokemonAbilitiesDomainModel("", 0, listOf())
            refreshLiveData()
            Resource.Success(PokemonResponse(0, "", 0, listOf()))
        }
    }

    override suspend fun getPokemonInfo(id: Int): Resource<Pokemon> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {

            val response = Pokemon(listOf(), id, Sprites(""), "pokeName")
            val ability = PokemonAbility("Poke Ability Name 1", id)
            val ability2 = PokemonAbility("Poke Ability Name 2", id)

            abilities.add(ability)
            abilities.add(ability2)

            pokemonWithAbilities =
                PokemonAbilitiesDomainModel("pokeName", id, listOf(ability.abilityName))
            refreshLiveData()
            Resource.Success(response)
        }
    }

    override suspend fun getPokemonWithAbilities(id: Int): PokemonAbilitiesDomainModel {
        val abilities = observableAbilities.value?.filter { it.pokeId == id }

        if (abilities != null) {
            pokemonWithAbilities = PokemonAbilitiesDomainModel("Pokemon Name", id, abilities.map { it.abilityName })
        }

        observablePokemonWithAbilities.postValue(pokemonWithAbilities)
        return observablePokemonWithAbilities.value!!
    }

    override fun allPokemonEntriesFromDB(): LiveData<List<PokemonDomainModel>> {
        return observablePokemonItems
    }

    override suspend fun checkDBForAbilitiesId(id: Int): List<PokemonAbility> {
        return observableAbilities.value?.toList()?.filter { it.pokeId == id } ?: listOf()
    }

    override suspend fun retrieveAllPokemonEntries(): List<PokemonEntry> {
        return checkPokemonEntriesList
    }
}