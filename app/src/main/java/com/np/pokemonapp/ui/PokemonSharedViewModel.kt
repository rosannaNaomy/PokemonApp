package com.np.pokemonapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.np.pokemonapp.domain.model.PokemonAbilitiesDomainModel
import com.np.pokemonapp.repository.IPokemonRepository
import com.np.pokemonapp.util.Constants.UNKNOWN_ERROR
import com.np.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonSharedViewModel @Inject constructor(
    private val repository: IPokemonRepository
) : ViewModel() {

    private val _pokemonWithAbilities = MutableLiveData<PokemonAbilitiesDomainModel>()
    val pokemonWithAbilities: LiveData<PokemonAbilitiesDomainModel>
        get() = _pokemonWithAbilities

    val pokemonList = repository.allPokemonEntriesFromDB()

    val loadError = MutableLiveData("")
    val isLoading = MutableLiveData(false)

    init {
        fetchAllPokemonFrom()
    }

    fun fetchAllPokemonFrom() {
        viewModelScope.launch {
            isLoading.value = true
            if (repository.isDataStored()) {
                isLoading.value = false
            } else {
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
    }

    fun fetchSinglePokemon(id: Int) {
        viewModelScope.launch {
            isLoading.value = true

            if (repository.isDataStored(id)) {
                _pokemonWithAbilities.value = repository.getPokemonWithAbilities(id)
                isLoading.value = false
            } else {
                val result = repository.getPokemonInfo(id)
                when (result) {
                    is Resource.Success -> {
                        val res = result.data?.id
                        _pokemonWithAbilities.value = res?.let { repository.getPokemonWithAbilities(it) }

                        loadError.value = ""
                        isLoading.value = false
                    }
                    is Resource.Error -> {
                        loadError.value = result.message ?: UNKNOWN_ERROR
                        isLoading.value = false
                    }
                }
            }
        }
    }


}