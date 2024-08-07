package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.databinding.RvContentMovieItemBinding
import com.rakarguntara.filmku.models.ResultsTopRatedItem

class MovieTopRatedAdapter: RecyclerView.Adapter<MovieTopRatedAdapter.MovieTopRatedViewHolder>() {

    private val movieTopRatedArrayList = ArrayList<ResultsTopRatedItem>()

    fun setData(list: List<ResultsTopRatedItem>){
        movieTopRatedArrayList.clear()
        movieTopRatedArrayList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MovieTopRatedViewHolder(private val binding: RvContentMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsTopRatedItem){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${item.posterPath}")
                .into(binding.ivMoviePopularItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTopRatedViewHolder {
        val binding = RvContentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTopRatedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieTopRatedArrayList.size
    }

    override fun onBindViewHolder(holder: MovieTopRatedViewHolder, position: Int) {
        val data : ResultsTopRatedItem = movieTopRatedArrayList[position]
        return holder.bind(data)
    }
}