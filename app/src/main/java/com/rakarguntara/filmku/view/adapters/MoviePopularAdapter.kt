package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.databinding.RvContentMovieItemBinding
import com.rakarguntara.filmku.models.ResultsItem

class  MoviePopularAdapter: RecyclerView.Adapter<MoviePopularAdapter.MoviePopularViewHolder>(){
    private val moviePopularArrayList = ArrayList<ResultsItem>()

    fun setData(list: List<ResultsItem>){
        moviePopularArrayList.clear()
        moviePopularArrayList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MoviePopularViewHolder(private val binding: RvContentMovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${item.posterPath}")
                .into(binding.ivMoviePopularItem)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePopularAdapter.MoviePopularViewHolder {
        val binding = RvContentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviePopularViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MoviePopularAdapter.MoviePopularViewHolder,
        position: Int
    ) {
        val data : ResultsItem = moviePopularArrayList[position]
        return holder.bind(data)
    }

    override fun getItemCount(): Int {
        return moviePopularArrayList.size
    }

}