package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL =
    "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {

    //pass in parsed movies into Movie list after Json callback
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    // 1. Define a data model class as the data source --done in Movie.kt
    // 2. Add the RecyclerView to the layout --done
    // 3. Create a custom row layout XML file to visualize the item --done
    // 4. Create an Adapter and ViewHolder to render the item --done
    // 5. Bind the adapter to the data source to populate the RecyclerView --done
    // 6. Bind a layout manager to the RecyclerView --done



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                //log error
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                //log info
                Log.i(TAG, "onSuccess: JSON data $json")
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    //notify adapter that the movie dataset has been updated
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")//doesnt log?
                } catch(e: JSONException){
                    Log.e(TAG, "Encountered JSON exception $e")
                }

            }

        })
    }
}