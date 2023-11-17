package com.migualador.cocktails.presentation.home.view_holders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.migualador.cocktails.presentation.BaseViewHolder
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState

/**
 * CocktailGridViewHolder
 *
 * Viewholder for showing cocktails in the list of cocktails
 */
class CocktailGridViewHolder(
    context: Context,
    itemView: View
): BaseViewHolder<CocktailUiState>(context, itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

    override fun bind(item: CocktailUiState) {
        Glide.with(context)
            .load(item.imageUrl)
            .fitCenter()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
        nameTextView.text = item.name

        itemView.setOnClickListener {
            item.onClick()
        }
    }

}