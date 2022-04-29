package com.foxhole.myapplication.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Flowable
import com.foxhole.myapplication.Model.Matches
import androidx.lifecycle.MutableLiveData
import com.foxhole.myapplication.Repository.MovieRepository

class GenreActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository: MovieRepository

    //Get all Genre
    val allGenre: Flowable<MutableList<Matches>>
        get() = movieRepository.getAllGenre()

    //Get Loading State
    val isLoading: MutableLiveData<Boolean>
        get() = movieRepository.isLoading

    //Insert Genre
    fun insert(matches: Matches) {
        movieRepository.insertGenre(matches)
    }

    //Update Genre
    fun updateGenre(matches: Matches) {
        movieRepository.updateGenre(matches)
    }

    //Delete Genre
    fun deleteGenre(matches: Matches?) {
        movieRepository.deleteGenre(matches!!)
    }

    //Delete All Genre
    fun deleteAllGenre() {
        movieRepository.deleteAllGenre()
    }

    init {
        movieRepository = MovieRepository(application)
    }
}