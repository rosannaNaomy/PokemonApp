package com.np.pokemonapp.ui

import android.util.Log
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

    private var _pokemonWithAbilitiesList = MutableLiveData<PokemonWithAbilities>()
    val pokemonWithAbilitiesList: MutableLiveData<PokemonWithAbilities>
        get() = _pokemonWithAbilitiesList

    private var _pokemonList = MutableLiveData<List<PokemonDomainModel>>()
    val pokemonList: MutableLiveData<List<PokemonDomainModel>>
        get() = _pokemonList

    var loadError = MutableLiveData("")
    var isLoading = MutableLiveData(false)

    init {
        fetchAllPokemon()
    }

    fun fetchAllPokemon() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList()
            when (result) {
                is Resource.Success -> {
                    _pokemonList.value = repository.allPokemonEntriesFromDB()
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