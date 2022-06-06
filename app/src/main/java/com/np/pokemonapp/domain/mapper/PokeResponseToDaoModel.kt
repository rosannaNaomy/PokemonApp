package com.np.pokemonapp.domain.mapper

import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.datasource.network.response.Ability
import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import com.np.pokemonapp.datasource.network.response.Result
import java.util.*

object PokeResponseToDaoModel {


    fun mapToDaoModel(result: Result): PokemonEntry {
        return PokemonEntry(
            pokemonName = result.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            url = result.url,
            id = getIdFromUrl(result.url)
        )
    }

    fun fromResponseList(pokemonResponse: PokemonResponse): List<PokemonEntry> {
        return pokemonResponse.results.map { mapToDaoModel(it) }
    }

    private fun getIdFromUrl(url: String): Int {
        val id = if (url.endsWith("/")) {
            url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            url.takeLastWhile { it.isDigit() }
        }
        return id.toInt()
    }

    fun mapToDaoModelPokemon(pokemon: Pokemon, ability: Ability): PokemonAbility {
        return PokemonAbility(
            abilityName = ability.ability.name,
            pokeId = pokemon.id
        )
    }

    fun fromSinglePokemonResponse(pokemon: Pokemon): List<PokemonAbility> {
        return pokemon.abilities.map { mapToDaoModelPokemon(pokemon,it)}
    }


}