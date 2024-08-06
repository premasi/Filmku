package com.rakarguntara.filmku.utils.animations

import android.widget.ImageView

fun animateIvClick(iv: ImageView) {
    iv.animate()
        .scaleX(0.95f)
        .scaleY(0.95f)
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