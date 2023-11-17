package com.migualador.cocktails.presentation.animation_extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

fun View.animateFadeOut() {
    val animation = AlphaAnimation(1.0f, 0.0f).apply {
        duration = 200
    }
    animation.setAnimationListener(object: Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            this@animateFadeOut.visibility = View.GONE
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    })
    this.startAnimation(animation)
}

fun View.animateDelayedFadeIn() {
    val animation = AlphaAnimation(0.0f, 1.0f).apply {
        duration = 200
        startOffset = 200
    }
    animation.setAnimationListener(object: Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            this@animateDelayedFadeIn.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(p0: Animation?) {}

        override fun onAnimationRepeat(p0: Animation?) {}
    })
    this.startAnimation(animation)
}