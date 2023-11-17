package com.migualador.cocktails.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState

/**
 * FeaturedCocktailsPagerAdapter
 *
 * Pager adapter for showing the list of featured cocktails at the top of the home
 */
class FeaturedCocktailsPagerAdapter(private val context: Context) : PagerAdapter() {

    private val rows: MutableList<CocktailDetailUiState> = arrayListOf()

    fun setData(featuredCocktails: List<CocktailDetailUiState>) {
        rows.clear()
        rows.addAll(featuredCocktails)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.card_featured_cocktail, container, false)
        renderView(view, position)
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }

    override fun getCount(): Int {
        return rows.size
    }

    override fun isViewFromObject(view: View, `object`: Any) = view === `object`

    private fun renderView(view: View, position: Int) {
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        val alcoholicTextView = view.findViewById<TextView>(R.id.alcoholicTextView)
        val glassTextView = view.findViewById<TextView>(R.id.glassTextView)

        val item = rows[position]

        Glide.with(context)
            .load(item.imageUrl)
            .fitCenter()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)

        nameTextView.text = item.drinkName
        categoryTextView.text = item.category
        glassTextView.text = item.glassType

        if (item.isAlcoholic) {
            alcoholicTextView.text = ContextCompat.getString(context, R.string.alcoholic_drink)
        } else {
            alcoholicTextView.text = ContextCompat.getString(context, R.string.non_alcoholic_drink)
            val drawableStart = ContextCompat.getDrawable(context, R.drawable.ic_no_alcohol_mini)
            alcoholicTextView.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
        }

        view.setOnClickListener { item.onClick() }

    }

}