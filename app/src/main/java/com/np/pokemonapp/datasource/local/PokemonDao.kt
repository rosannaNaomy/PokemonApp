package com.np.pokemonapp.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.np.pokemonapp.datasource.local.entities.PokemonEntry

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntry: PokemonEntry)

    @Query("SELECT * FROM pokemon_entries")
    fun observeAllPokemonEntries(): LiveData<List<PokemonEntry>>

    @Query("DELETE FROM pokemon_entries")
    fun deleteAll()
}