package com.example.flixster

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import androidx.core.util.Pair

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val REGULAR = 0
    private val POPULAR = 1

    override fun getItemViewType(position: Int): Int {
        if (movies[position].rating > 5) {
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

        init {
            itemView.setOnClickListener(this)//pass in the class
        }

        fun bind(movie: Movie) {
            //populate corresponding parts of itemView with data in movie
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            //check screen orientation
            val ori = context.resources.configuration.orientation
            //rounded corners config
            val radius = 50
            val margin = 10


            if (ori == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .transform(RoundedCornersTransformation(radius, margin))
                    .into(ivPoster)
            } else {
                Glide.with(context)
                    .load(movie.posterLandImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .transform(RoundedCornersTransformation(radius, margin))
                    .into(ivPoster)
            }
        }

        override fun onClick(p0: View?) {
            // 1. Get notified of the particular movie which was clicked on
            val movie = movies[adapterPosition]
            // 2. Use intent to navigate to the new activity(screen)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                Pair<View, String>(tvTitle, "tvTitle-transition"),
                Pair<View, String>(
                    tvOverview, "tvOverview-transition"
                )
            )
            context.startActivity(intent, options.toBundle());
        }
    }
}