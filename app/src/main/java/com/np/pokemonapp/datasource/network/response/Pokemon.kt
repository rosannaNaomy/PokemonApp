package com.np.pokemonapp.datasource.network.response

data class Pokemon(
val abilities: List<Ability>,
val id: Int,
val sprites: Sprites,
val name: String
)