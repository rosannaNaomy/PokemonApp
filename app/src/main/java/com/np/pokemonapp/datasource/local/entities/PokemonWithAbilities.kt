package com.np.pokemonapp.datasource.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonWithAbilities (
    @Embedded val pokemon: PokemonEntry,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokeId"
    )
    val pokemonAbilities: List<PokemonAbility>
)