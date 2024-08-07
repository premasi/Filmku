package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rakarguntara.filmku.databinding.RvContentGenreItemBinding
import com.rakarguntara.filmku.models.GenresItem

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val genreArrayList = ArrayList<GenresItem>()

    fun setData(list: List<GenresItem>){
        genreArrayList.clear()
        genreArrayList.addAll(list)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val binding: RvContentGenreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GenresItem){
            val name = item.name + ", "
            binding.tvMovieGenre.text = name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = RvContentGenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genreArrayList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data : GenresItem = genreArrayList[position]
        return holder.bind(data)
    }
}