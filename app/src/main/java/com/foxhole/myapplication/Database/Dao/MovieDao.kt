package com.foxhole.myapplication.Database.Dao

import androidx.room.*
import com.foxhole.myapplication.Model.Movie
import io.reactivex.Flowable

@Dao
interface MovieDao {
    //Insert Movie
    @Insert
    fun insert(movie: Movie)

    //Update existing movie
    @Update
    fun update(movie: Movie)

    //Delete existing movie
    @Delete
    fun delete(movie: Movie)

    //Delete all movies from table;
    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    //Get Movie list under specific genre
    @Query("SELECT * FROM movie_table WHERE genre_id LIKE :genre_id")
    fun getAllMoviesByGenre(genre_id: Int): Flowable<MutableList<Movie>>

    //    WHERE genre_id==:genre_id int genre_id
    //Delete movies by genre
    @Query("DELETE FROM movie_table WHERE genre_id==:genre_id")
    fun deleteAllMoviesUnderGenre(genre_id: Int)
}