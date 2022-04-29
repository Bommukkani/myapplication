package com.foxhole.myapplication.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Flowable
import androidx.lifecycle.MutableLiveData
import com.foxhole.myapplication.Model.Movie
import com.foxhole.myapplication.Repository.MovieRepository

class MovieActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository: MovieRepository

    //Get all Movie under the specific Genre
    fun getAllMovies(genre_id: Int): Flowable<MutableList<Movie>> {
        return movieRepository.getAllMovies(genre_id)
    }

    //Get Loading State
    val isLoading: MutableLiveData<Boolean>
        get() = movieRepository.isLoading

    //Insert Movie
    fun insert(movie: Movie) {
        movieRepository.insertMovie(movie)
    }

    //Update Movie
    fun update(movie: Movie) {
        movieRepository.updateMovie(movie)
    }

    //Delete Movie
    fun delete(movie: Movie) {
        movieRepository.deleteMovie(movie)
    }

    //Delete All Movie
    fun deleteAllMoviesByGenre(genre_id: Int) {
        movieRepository.deleteAllMoviesByGenre(genre_id)
    }

    init {
        movieRepository = MovieRepository(application)
    }
}