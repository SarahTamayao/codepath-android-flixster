package com.example.flixster

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val REGULAR = 0
    private val POPULAR = 1

    override fun getItemViewType(position: Int): Int {
        if (movies[position].popularity > 5) {
            //popular
            return POPULAR
        } else {
            return REGULAR
        }
        return -1
    }

    //Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
//        when (viewType) {
//            POPULAR -> {
//                val v1 = inflater.inflate(R.layout.item_popular_movie, parent, false)
//                return ViewHolder(v1)
//            }
//            else -> {//REGULAR
//                val v0 = inflater.inflate(R.layout.item_movie, parent, false)
//                return ViewHolder(v0)
//            }
//        }
    }

    //Cheap: simply bind data an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //bind data at specific position with ViewHolder
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size //inline one-line function

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

        fun bind(movie: Movie) {
            //populate corresponding parts of itemView with data in movie
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            //check screen orientation
            val ori = context.resources.configuration.orientation
            if (ori == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivPoster)
            } else {
                Glide.with(context)
                    .load(movie.posterLandImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivPoster)
            }
        }
    }

//    inner class ViewHolderPopular(itemView: View) : RecyclerView.ViewHolder(itemView){
//        private val ivBackdrop = itemView.findViewById<ImageView>(R.id.ivBackdrop)
//
//        fun bind(movie: Movie){
//            Glide.with(context)
//                .load(movie.posterLandImageUrl)
//                .placeholder(R.drawable.ic_launcher_background)
//                .into(ivBackdrop)
//        }
//    }
}
