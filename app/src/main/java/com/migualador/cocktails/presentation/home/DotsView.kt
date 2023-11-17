package com.migualador.cocktails.presentation.home

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.migualador.cocktails.R

/**
 * DotsView
 *
 * View that shows circles representing the selected page in a view pager
 */
class DotsView @JvmOverloads constructor(
        private val ctx: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): LinearLayout(ctx, attrs, defStyleAttr) {

    private var currentSelectedPage = 0

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    private val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun setNumberOfPages(pages: Int) {
        removeAllViews()

        for(i in 0 until pages) {
            val imageView = ImageView(ctx)
            imageView.setImageResource(R.drawable.dot_small)
            val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.marginEnd = 1.toPx
            layoutParams.marginStart = 1.toPx
            imageView.layoutParams = layoutParams
            addView(imageView)
        }
    }

    fun setSelectedPage(newSelectedPage: Int) {
        val currentSelectedDotView = getChildAt(currentSelectedPage)
        currentSelectedDotView?.let {
            (it as ImageView).setImageResource(R.drawable.dot_small)
        }


        val newSelectedDotView = getChildAt(newSelectedPage)
        newSelectedDotView?.let {
            (it as ImageView).setImageResource(R.drawable.dot_big)
        }

        currentSelectedPage = newSelectedPage
    }

}
