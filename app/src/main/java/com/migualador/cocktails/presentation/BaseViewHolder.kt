package com.migualador.cocktails.presentation

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseViewHolder
 *
 */
abstract class BaseViewHolder<T>(var context: Context, itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)

}