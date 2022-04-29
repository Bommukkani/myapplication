package com.foxhole.myapplication.Database.Dao

import androidx.room.*
import com.foxhole.myapplication.Model.Matches
import io.reactivex.Flowable

@Dao
interface GenreDao {
    //Insert new Genre
    @Insert
    fun insert(genre: Matches)

    //Update existing Genre
    @Update
    fun update(genre: Matches)

    //Delete Specific Genre and also delete movies under this genre
    @Delete
    fun delete(genre: Matches)

    //Delete all Genre from table
    @Query("DELETE FROM matches_table")
    fun deleteAllGenre()

    //Get all Genre
    //Get all Genre from table
    @Query("SELECT * FROM matches_table")
    fun getAllGenres(): Flowable<MutableList<Matches>>
}