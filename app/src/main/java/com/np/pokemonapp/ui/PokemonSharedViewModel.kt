package com.np.pokemonapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.repository.PokemonRepository
import com.np.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSharedViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Resource<PokemonResponse>>>()
    val pokemonList: LiveData<List<Resource<PokemonResponse>>>
        get() = _pokemonList

    var loadError = MutableLiveData("")
    var isLoading = MutableLiveData(false)

    init {
        fetchPokemon()
    }

    fun fetchPokemon() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList()
            when(result) {
                is Resource.Success -> {
                    _pokemonList.postValue(listOf(result))
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