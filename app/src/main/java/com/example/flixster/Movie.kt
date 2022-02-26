package com.example.flixster

import org.json.JSONArray

data class Movie(
    //represents one movie
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val popularity: Int
){
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    val posterLandImageUrl = "https://image.tmdb.org/t/p/w342/$backdropPath"
    //pass Json file to Kotlin class
    companion object {
        //allows us to call methods of the Movie class without having an instance
        //similar to class methods
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            //iterate through the JSONarray to return a list of Movie
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getString("backdrop_path"),
                        movieJson.getInt("vote_average")
                    )
                )
            }
            return movies;
        }
    }

}