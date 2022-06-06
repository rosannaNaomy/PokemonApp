package com.np.pokemonapp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry

@Database(
    entities = [PokemonEntry::class,
               PokemonAbility::class],
    version = 1
)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}