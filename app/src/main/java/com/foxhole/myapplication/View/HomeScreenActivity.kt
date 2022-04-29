package com.foxhole.myapplication.View


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.foxhole.myapplication.Adapter.GenreAdapter
import com.foxhole.myapplication.Adapter.GenreAdapter.OnGenreClickListener
import com.foxhole.myapplication.Model.Matches
import com.foxhole.myapplication.R
import com.foxhole.myapplication.View.MoviesActivity
import com.foxhole.myapplication.ViewModel.GenreActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class HomeScreenActivity : AppCompatActivity(), OnGenreClickListener {
    private var genreActivityViewModel: GenreActivityViewModel? = null
    private val compositeDisposable = CompositeDisposable()
    private var mRecyclerView: RecyclerView? = null
    private var genreAdapter: GenreAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)
        mRecyclerView = findViewById(R.id.recycler_view)
//        genreActivityViewModel = ViewModelProviders.of(this).get(
//            GenreActivityViewModel::class.java
//
//        )
        genreActivityViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
                GenreActivityViewModel::class.java
            )


        val disposable = genreActivityViewModel!!.allGenre.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { genres ->
                Log.d(TAG, "accept: Called")
                val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                if (sh.getString("data", "").equals("", ignoreCase = true)) {
                genres.add(
                        Matches(
                            "Priya",
                            "29 years",
                            "5 ft 2 in",
                            "Chennai",
                            "Doctor",
                            R.drawable.red
                        )
                    )
                    genres.add(
                        Matches(
                            "Sakthi",
                            "27 years",
                            "5 ft 5 in",
                            "Madurai",
                            "Engineer",
                            R.drawable.rose
                        )
                    )
                    genres.add(
                        Matches(
                            "Mythili",
                            "25 years",
                            "5 ft 3 in",
                            "Kanchipuram",
                            "Teacher",
                            R.drawable.white
                        )
                    )
                    genres.add(
                        Matches(
                            "Gayathri",
                            "23 years",
                            "5 ft 6 in",
                            "Dharmapuri",
                            "IT",
                            R.drawable.orange
                        )
                    )
                    genres.add(
                        Matches(
                            "Maha",
                            "22 years",
                            "5 ft 1 in",
                            "Selam",
                            "Fashion Designer",
                            R.drawable.flower
                        )
                    )
                    for (i in genres.indices) {
                        saveNewGenre(genres[i])
                    }
                    val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("data", "data")
                    myEdit.commit()
                } else {
                    Log.e("CheckData", "Empty")
                }
                setDataToRecyclerView(genres)
            }


        //Add Disposable
        compositeDisposable.add(disposable)
    }

    private fun setDataToRecyclerView(matches: List<Matches>) {
        genreAdapter = GenreAdapter(matches)
        genreAdapter!!.setItemClickListener(this)
        mRecyclerView!!.adapter = genreAdapter
    }

    // @Override
    fun saveNewGenre(genre: Matches) {
        Log.d(TAG, "saveNewGenre: " + genre.name)
        genreActivityViewModel!!.insert(genre)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Remove Disposable
        compositeDisposable.dispose()
    }

    override fun onGenreClick(genre: Matches?) {
        Log.d(TAG, "onGenreClick: onItemClick")
        moveToMoviesActivity(genre)
    }

    override fun onDelClick(pos: Int) {
        genreActivityViewModel!!.deleteGenre(genreAdapter!!.getGenreAt(pos))
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
    }

    fun moveToMoviesActivity(genre: Matches?) {
        try {
            val intent = Intent(this@HomeScreenActivity, MoviesActivity::class.java)
            intent.putExtra("genre", genre!!.name)
            intent.putExtra("uid", genre.uid)
            intent.putExtra(
                "details",
                genre.age + ", " + genre.hgt + ", " + genre.occupation + ", " + genre.place
            )
            startActivity(intent)
        } catch (e: Exception) {
            Log.v("HomeScreen:", e.message!!)
        }
    }

    companion object {
        private const val TAG = "MainActivity_TAG"
    }
}