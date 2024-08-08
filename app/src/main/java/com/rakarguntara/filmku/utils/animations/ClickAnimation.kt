package com.rakarguntara.filmku.utils.animations

import android.view.View
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView

fun animateMcvClick(mcv: MaterialCardView) {
    mcv.animate()
        .scaleX(0.95f)
        .scaleY(0.95f)
        .setDuration(100)
        .withEndAction {
            // Reset the scale back to normal
            mcv.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .start()
        }
        .start()
}

fun animateIvClick(iv: ImageView) {
    iv.animate()
        .scaleX(0.80f)
        .scaleY(0.80f)
        .setDuration(100)
        .withEndAction {
            // Reset the scale back to normal
            iv.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .start()
        }
        .start()
}

fun animateViewClick(view: View) {
    view.animate()
        .scaleX(0.80f)
        .scaleY(0.80f)
        .setDuration(100)
        .withEndAction {
            // Reset the scale back to normal
            view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .start()
        }
        .start()
}