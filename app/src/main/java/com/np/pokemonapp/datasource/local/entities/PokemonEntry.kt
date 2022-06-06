package com.np.pokemonapp.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_entries")
data class PokemonEntry(
    @PrimaryKey
    val pokemonName: String,
    val url: String,
    val id: Int
)