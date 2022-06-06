package com.np.pokemonapp.domain.mapper

import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.domain.model.PokemonDomainModel

object DaoModelToDomainModel {

    fun mapToDomainModel(pokemonEntry: PokemonEntry): PokemonDomainModel {
        return PokemonDomainModel(
            name = pokemonEntry.pokemonName,
            id = pokemonEntry.id
        )
    }

    fun fromDatabaseList(pokemonEntries: List<PokemonEntry>): List<PokemonDomainModel> {
        return pokemonEntries.map { mapToDomainModel(it) }
    }

}