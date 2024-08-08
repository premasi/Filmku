package com.rakarguntara.filmku.utils.listener

import com.rakarguntara.filmku.models.DetailMovieResponse

interface OnItemViewClickListener {
    fun onMovieItemClick(detailMovieResponse: DetailMovieResponse)
}