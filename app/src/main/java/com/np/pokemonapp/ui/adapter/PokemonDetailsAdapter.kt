package com.np.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.np.pokemonapp.R
import com.np.pokemonapp.datasource.local.entities.PokemonAbility
import kotlinx.android.synthetic.main.ability_item.view.*
import kotlinx.android.synthetic.main.fragment_pokemon_details.view.*
import javax.inject.Inject

class PokemonDetailsAdapter @Inject constructor(): RecyclerView.Adapter<PokemonDetailsAdapter.PokemonDetailsViewHolder>() {
    class PokemonDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val listData = mutableListOf<String>()

    fun updateList(pokemonAbilityList: List<String>) {
        this.listData.clear()
        this.listData.addAll(pokemonAbilityList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailsViewHolder {
        return PokemonDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ability_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonDetailsViewHolder, position: Int) {
        val abilityname = listData[position]
        holder.itemView.apply {
            ability_name_textview.text = abilityname
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}