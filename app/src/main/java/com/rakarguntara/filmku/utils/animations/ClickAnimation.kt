package com.rakarguntara.filmku.utils.animations

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