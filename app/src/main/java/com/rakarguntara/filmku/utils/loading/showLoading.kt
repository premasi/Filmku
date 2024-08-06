package com.rakarguntara.filmku.utils.loading

import android.view.View
import android.widget.ProgressBar

fun showLoading(pb: ProgressBar, status: Boolean) {
    if(status){
        pb.visibility = View.VISIBLE
    } else {
        pb.visibility = View.GONE
    }
}