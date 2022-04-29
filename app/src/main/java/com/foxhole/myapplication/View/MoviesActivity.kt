package com.foxhole.myapplication.View


import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import com.foxhole.myapplication.ViewModel.MovieActivityViewModel
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import com.foxhole.myapplication.R
import androidx.lifecycle.ViewModelProviders
import com.foxhole.myapplication.Adapter.ProfileAdapter
import com.foxhole.myapplication.Model.Movie
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.ArrayList

class MoviesActivity : AppCompatActivity() {
    private var mGenre: String? = ""
    private var details: String? = ""
    private var genre_id = 0
    private val compositeDisposable = CompositeDisposable()
    private var movieActivityViewModel: MovieActivityViewModel? = null
    var coverFlowAdapter: ProfileAdapter? = null
    var coverFlowView: FeatureCoverFlow? = null
    var tvName: TextView? = null
    var tvDetails: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        coverFlowView = findViewById(R.id.coverflow)
        tvName = findViewById(R.id.tvName)
        tvDetails = findViewById(R.id.tvDetails)
        val extras = intent.extras
        if (extras != null) {
            mGenre = extras.getString("genre")
            details = extras.getString("details")
            genre_id = extras.getInt("uid")
            Log.d(TAG, "onStart: $genre_id$mGenre")
            tvName?.setText("" + mGenre)
            tvDetails?.setText("" + details)

        }
        movieActivityViewModel = ViewModelProviders.of(this).get(
            MovieActivityViewModel::class.java
        )
        val disposable =
            movieActivityViewModel!!.getAllMovies(genre_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movies ->
                    var movies = movies
                    Log.d(TAG, "accept: getAllMovies")
                    if (movies == null || movies.size == 0) {
                        if (movies == null) movies = ArrayList()
                        movies.add(Movie(R.drawable.red))
                        movies.add(Movie(R.drawable.flower))
                        movies.add(Movie(R.drawable.rose))
                        movies.add(Movie(R.drawable.white))
                        movies.add(Movie(R.drawable.orange))
                        for (i in movies.indices) {
                            saveNewMovie(movies[i])
                        }
                    }
                    coverFlowAdapter = ProfileAdapter(this@MoviesActivity, movies)
                    coverFlowView?.setAdapter(coverFlowAdapter)
                    //                        for (Movie movie: movies){
//                            Log.d(TAG, "accept: "+movie.getTitle());
//                        }
                }
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun saveNewMovie(movie: Movie) {
        movie.genre_id = genre_id
        movieActivityViewModel!!.insert(movie)
    }

    companion object {
        private const val TAG = "MoviesActivity_Tag"
    }
}