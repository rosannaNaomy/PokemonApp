package com.np.pokemonapp.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_abilities")
data class PokemonAbility (
    @PrimaryKey val abilityName: String,
    val pokeId: Int
)