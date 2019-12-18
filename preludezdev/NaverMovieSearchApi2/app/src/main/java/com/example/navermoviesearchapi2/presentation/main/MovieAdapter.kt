package com.example.navermoviesearchapi2.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navermoviesearchapi2.R
import com.example.navermoviesearchapi2.base.BaseViewHolder
import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import com.example.navermoviesearchapi2.databinding.ItemMovieBinding

class MovieAdapter(private val clickEvent: (url: String) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val data = mutableListOf<MovieListResponse.Item>()

    fun setData(newData: List<MovieListResponse.Item>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent, clickEvent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindTo(data[position])

    class MovieViewHolder(parent: ViewGroup, clickEvent: (url: String) -> Unit) :
        BaseViewHolder<ItemMovieBinding>(R.layout.item_movie, parent) {

        private var movie: MovieListResponse.Item? = null

        init {
            itemView.setOnClickListener {
                movie?.let { movie ->
                    clickEvent(movie.link)
                }
            }
        }

        fun bindTo(movie: MovieListResponse.Item) {
            binding.movie = movie
            this.movie = movie
        }
    }
}