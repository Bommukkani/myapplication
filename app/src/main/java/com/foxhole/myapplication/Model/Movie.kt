package com.foxhole.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class Movie(  //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
    var genre_id = 0

}