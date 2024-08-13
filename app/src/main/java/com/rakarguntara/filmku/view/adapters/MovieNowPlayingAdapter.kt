package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.RvContentMovieItemBinding
import com.rakarguntara.filmku.models.ResultsNowPlayingItem
import com.rakarguntara.filmku.utils.animations.animateMcvClick
import com.rakarguntara.filmku.utils.listener.OnMovieItemClickListener

class MovieNowPlayingAdapter: RecyclerView.Adapter<MovieNowPlayingAdapter.MovieNowPlayingViewHolder>() {
    private val movieNowPlayingArrayList = ArrayList<ResultsNowPlayingItem>()
    private var onMovieItemClickListener: OnMovieItemClickListener? = null

    fun setData(list: List<ResultsNowPlayingItem>){
        movieNowPlayingArrayList.clear()
        movieNowPlayingArrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun onMovieItemClickListener(onMovieItemClickListener: OnMovieItemClickListener){
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    inner class MovieNowPlayingViewHolder(private val binding: RvContentMovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsNowPlayingItem){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNowPlayingViewHolder {
        val binding = RvContentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MovieNowPlayingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieNowPlayingArrayList.size
    }

    override fun onBindViewHolder(holder: MovieNowPlayingViewHolder, position: Int) {
        val data: ResultsNowPlayingItem = movieNowPlayingArrayList[position]
        return holder.bind(data)
    }
}