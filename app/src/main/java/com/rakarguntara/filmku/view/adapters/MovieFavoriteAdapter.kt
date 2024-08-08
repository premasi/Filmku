package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.databinding.RvContentMovieFavoritesItemBinding
import com.rakarguntara.filmku.models.DetailMovieResponse

class MovieFavoriteAdapter: RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder>() {

    private val movieFavoriteArrayList = ArrayList<DetailMovieResponse>()

    fun setData(list: List<DetailMovieResponse>){
        movieFavoriteArrayList.clear()
        movieFavoriteArrayList.addAll(list)
        notifyDataSetChanged()
    }

    class MovieFavoriteViewHolder(private val binding: RvContentMovieFavoritesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailMovieResponse){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${item.posterPath}")
                .into(binding.ivMoviePoster)

            binding.tvMovieTitle.text = item.title
            binding.tvMovieInformationDateActual.text = item.releaseDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val binding = RvContentMovieFavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieFavoriteArrayList.size
    }

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        val data: DetailMovieResponse = movieFavoriteArrayList[position]
        return holder.bind(data)
    }
}