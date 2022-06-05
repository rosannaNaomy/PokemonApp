package com.np.pokemonapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.pokemonapp.domain.model.PokemonEntry
import com.np.pokemonapp.repository.PokemonRepository
import com.np.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSharedViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var _pokemonList = MutableLiveData<List<PokemonEntry>>()
    val pokemonList: MutableLiveData<List<PokemonEntry>>
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
            when (result) {
                is Resource.Success -> {
                    _pokemonList.value = result.data?.results?.map {
                        PokemonEntry(it.name, it.url, 0 )
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