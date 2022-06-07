package com.np.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.np.pokemonapp.R
import com.np.pokemonapp.domain.model.PokemonDomainModel
import kotlinx.android.synthetic.main.pokemon_item.view.*
import javax.inject.Inject

class PokemonNamesListAdapter @Inject constructor(): RecyclerView.Adapter<PokemonNamesListAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val listData = mutableListOf<PokemonDomainModel>()

    fun updateList(pokemonList: List<PokemonDomainModel>) {
        this.listData.clear()
        this.listData.addAll(pokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_item,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokename = listData[position].name
        val pokeId = listData[position].id
        holder.itemView.apply {
            pokemon_name_textview.text = pokename

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(pokeId)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}