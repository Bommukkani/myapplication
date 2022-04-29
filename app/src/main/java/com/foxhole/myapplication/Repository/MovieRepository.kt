package com.foxhole.myapplication.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.foxhole.myapplication.Database.Dao.GenreDao
import com.foxhole.myapplication.Database.Dao.MovieDao
import com.foxhole.myapplication.Database.MovieDatabase.Companion.getInstance
import com.foxhole.myapplication.Model.Matches
import com.foxhole.myapplication.Model.Movie
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieRepository(application: Application) {
    private val genreDao: GenreDao?
    private val movieDao: MovieDao?
    private val allGenres: Flowable<List<Matches>>? = null
    private val allMovies: Flowable<List<Movie>>? = null

    //Get Loading State
    var isLoading = MutableLiveData<Boolean>()

    //Get all Genre

    fun getAllGenre(): Flowable<MutableList<Matches>> {
        return genreDao!!.getAllGenres()
    }
    //Get all Movie under the specific Genre
    fun getAllMovies(genre_id: Int): Flowable<MutableList<Movie>> {
        return movieDao!!.getAllMoviesByGenre(genre_id)
    }

    //Insert Genre
    fun insertGenre(matches: Matches) {
        isLoading.value = true
        Completable.fromAction { genreDao!!.insert(matches) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    //Update Genre
    fun updateGenre(matches: Matches) {
        Completable.fromAction { genreDao!!.update(matches) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: Called")
                }
            })
    }

    //Delete Genre
    fun deleteGenre(matches: Matches) {
        isLoading.value = true
        Completable.fromAction { genreDao!!.delete(matches) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    deleteAllMoviesByGenre(matches.uid)
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    //Delete all Genre
    fun deleteAllGenre() {
        isLoading.value = true
        Completable.fromAction { genreDao!!.deleteAllGenre() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    deleteAllMovies()
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: Called" + e.message)
                }
            })
    }

    //Insert Movie
    fun insertMovie(movie: Movie) {
        isLoading.value = true
        Completable.fromAction { movieDao!!.insert(movie) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: Called" + e.message)
                }
            })
    }

    //Update Movie
    fun updateMovie(movie: Movie) {
        Completable.fromAction { movieDao!!.update(movie) }.observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: Called" + e.message)
                }
            })
    }

    //Delete Movie
    fun deleteMovie(movie: Movie) {
        isLoading.value = true
        Completable.fromAction { movieDao!!.delete(movie) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    //Delete all Movie
    fun deleteAllMovies() {
        isLoading.value = true
        Completable.fromAction { movieDao!!.deleteAllMovies() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    //Delete all Movies By Genre
    fun deleteAllMoviesByGenre(genre_id: Int) {
        isLoading.value = true
        Completable.fromAction { movieDao!!.deleteAllMoviesUnderGenre(genre_id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }
            })
    }

    companion object {
        private const val TAG = "MovieRepository"
    }

    init {
        val movieDatabase = getInstance(application!!)
        genreDao = movieDatabase!!.genreDao()
        movieDao = movieDatabase.movieDao()
    }
}