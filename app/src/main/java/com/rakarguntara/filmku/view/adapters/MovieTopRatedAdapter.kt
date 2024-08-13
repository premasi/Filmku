package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.RvContentMovieItemBinding
import com.rakarguntara.filmku.models.ResultsTopRatedItem
import com.rakarguntara.filmku.utils.animations.animateMcvClick
import com.rakarguntara.filmku.utils.listener.OnMovieItemClickListener

class MovieTopRatedAdapter: RecyclerView.Adapter<MovieTopRatedAdapter.MovieTopRatedViewHolder>() {

    private val movieTopRatedArrayList = ArrayList<ResultsTopRatedItem>()
    private var onMovieItemClickListener: OnMovieItemClickListener? = null

    fun setData(list: List<ResultsTopRatedItem>){
        movieTopRatedArrayList.clear()
        movieTopRatedArrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun onMovieItemClickListener(onMovieItemClickListener: OnMovieItemClickListener){
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    inner class MovieTopRatedViewHolder(private val binding: RvContentMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsTopRatedItem){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${item.posterPath}")
                .placeholder(R.drawable.iv_thank)
                .error(R.drawable.iv_thank)
                .into(binding.ivMoviePopularItem)

            binding.mcvMoviePopularItem.setOnClickListener {
                animateMcvClick(binding.mcvMoviePopularItem)
                onMovieItemClickListener?.onMovieItemClick(item.id)
            }
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