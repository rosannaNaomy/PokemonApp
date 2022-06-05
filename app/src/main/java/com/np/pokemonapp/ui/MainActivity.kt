package com.np.pokemonapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.np.pokemonapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[ PokemonSharedViewModel::class.java]
        viewModel.fetchPokemon()
        subscribe()
    }

    fun subscribe(){
        viewModel.pokemonList.observe(this){
            val name = it[0].data?.results?.get(0)?.name
            val size = it[0].data?.results?.size
            Log.d("MainActivity", "${name.toString()} $size" )
        }
    }
}