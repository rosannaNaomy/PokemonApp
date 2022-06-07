package com.np.pokemonapp.datasource.network

import com.np.pokemonapp.datasource.network.response.Pokemon
import com.np.pokemonapp.datasource.network.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ): Pokemon

}