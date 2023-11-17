package com.migualador.cocktails.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState
import com.migualador.cocktails.presentation.home.view_holders.CocktailViewHolder

/**
 * CocktailsAdapter
 *
 */
class CocktailsAdapter(private val context: Context) : RecyclerView.Adapter<CocktailViewHolder>() {

    private val rows: MutableList<CocktailUiState> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_cocktail, parent, false)
        return CocktailViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(rows[position])
    }

    fun setData(cocktails: List<CocktailUiState>) {
        rows.clear()
        rows.addAll(cocktails)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return rows.size
    }
}