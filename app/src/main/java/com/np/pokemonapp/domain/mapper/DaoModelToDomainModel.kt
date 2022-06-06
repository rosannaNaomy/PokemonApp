package com.np.pokemonapp.domain.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.domain.model.PokemonDomainModel

object DaoModelToDomainModel {

    private fun mapToDomainModel(pokemonEntry: PokemonEntry): PokemonDomainModel {
        return PokemonDomainModel(
            name = pokemonEntry.pokemonName,
            id = pokemonEntry.id
        )
    }

    fun fromDatabaseList(pokemonEntries: LiveData<List<PokemonEntry>>): LiveData<List<PokemonDomainModel>> {
        return Transformations.map(pokemonEntries) { it ->
            return@map it.map { mapToDomainModel(it) }
        }
    }

}