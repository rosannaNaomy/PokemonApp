package com.np.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.np.pokemonapp.R
import com.np.pokemonapp.domain.model.PokemonDomainModel
import kotlinx.android.synthetic.main.pokemon_item.view.*
import javax.inject.Inject

class PokemonAdapter @Inject constructor(): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listData = mutableListOf<PokemonDomainModel>()

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

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokename = listData[position].name
        holder.itemView.apply {
            pokemon_name_textview.text = pokename

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(pokename.lowercase())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}