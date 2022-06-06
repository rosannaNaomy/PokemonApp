package com.np.pokemonapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.pokemonapp.datasource.local.entities.PokemonWithAbilities
import com.np.pokemonapp.domain.model.PokemonDomainModel
import com.np.pokemonapp.repository.PokemonRepository
import com.np.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSharedViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonWithAbilitiesList = MutableLiveData<PokemonWithAbilities>()
    val pokemonWithAbilitiesList: LiveData<PokemonWithAbilities>
        get() = _pokemonWithAbilitiesList

    val pokemonList = repository.allPokemonEntriesFromDB()

    val loadError = MutableLiveData("")
    val isLoading = MutableLiveData(false)

    init {
        fetchAllPokemon()
    }

    private fun fetchAllPokemon() {
        viewModelScope.launch {
            isLoading.value = true
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
    }

    fun fetchSinglePokemon(name:String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonInfo(name)
            when (result) {
                is Resource.Success -> {
                    val id = result.data?.id
                    _pokemonWithAbilitiesList.value = id?.let {
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

}