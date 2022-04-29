package com.foxhole.myapplication.Database

import android.content.Context
import androidx.room.Database
import com.foxhole.myapplication.Model.Matches
import androidx.room.RoomDatabase
import com.foxhole.myapplication.Database.Dao.GenreDao
import com.foxhole.myapplication.Database.Dao.MovieDao
import com.foxhole.myapplication.Database.MovieDatabase
import kotlin.jvm.Synchronized
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.foxhole.myapplication.Model.Movie

@Database(entities = [Matches::class, Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao?
    abstract fun movieDao(): MovieDao?

    companion object {
        private var instance: MovieDatabase? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): MovieDatabase? {
            if (instance == null) {
                //If instance is null that's mean database is not created and create new database
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build()
            }
            return instance
        }

        private val roomCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}