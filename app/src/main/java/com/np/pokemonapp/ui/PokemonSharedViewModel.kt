package com.np.pokemonapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.repository.PokemonRepository
import com.np.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSharedViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonWithAbilities = MutableLiveData<PokemonAbilitiesDomainModel>()
    val pokemonWithAbilities: LiveData<PokemonAbilitiesDomainModel>
        get() = _pokemonWithAbilities

    val pokemonList = repository.allPokemonEntriesFromDB()

    val loadError = MutableLiveData("")
    val isLoading = MutableLiveData(false)

    init {
        fetchAllPokemonFromDB()
    }

    private fun fetchAllPokemonFromDB() {
        viewModelScope.launch {
            isLoading.value = true
            val pokemonInDB = repository.retrieveAllPokemonEntries()
            if (pokemonInDB.isNotEmpty()) {
                isLoading.value = false
            } else {
                fetchAllPokemonFromNetwork()
            }
        }
    }

    private suspend fun fetchAllPokemonFromNetwork() {
        val result = repository.getPokemonList()
        when (result) {
            is Resource.Success -> {
                loadError.value = ""
                isLoading.value = false
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                isLoading.value = false
            }
        }

    }

    fun fetchSinglePokemonFromDB(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val abilities = repository.checkDBForAbilitiesId(id)

            if (abilities.isNotEmpty()) {
                _pokemonWithAbilities.value = repository.getPokemonWithAbilities(id)
                isLoading.value = false
            } else {
                fetchSinglePokemonFromNetwork(id)
            }
        }
    }

    private suspend fun fetchSinglePokemonFromNetwork(id: Int) {
        val result = repository.getPokemonInfo(id)
        when (result) {
            is Resource.Success -> {
                val res = result.data?.id
                _pokemonWithAbilities.value = res?.let {
                    repository.getPokemonWithAbilities(it)
                }
                loadError.value = ""
                isLoading.value = false
            }
            is Resource.Error -> {
                loadError.value = result.message!!
                isLoading.value = false
            }
        }
    }

}