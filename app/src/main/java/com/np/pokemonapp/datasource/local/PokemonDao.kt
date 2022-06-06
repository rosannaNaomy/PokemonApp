package com.np.pokemonapp.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.datasource.local.entities.PokemonWithAbilities

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntry: PokemonEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbility(pokemonAbility: PokemonAbility)

    @Query("SELECT * FROM pokemon_entries")
    fun observeAllPokemonEntries(): LiveData<List<PokemonEntry>>

    @Query("DELETE FROM pokemon_entries")
    suspend fun deleteAll()

    @Query ("SELECT * FROM pokemon_entries where id = :id LIMIT 1")
    suspend fun getPokemonWithAbilities(id:Int): PokemonWithAbilities
}