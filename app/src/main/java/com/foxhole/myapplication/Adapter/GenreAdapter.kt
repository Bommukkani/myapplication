package com.foxhole.myapplication.Adapter


import com.foxhole.myapplication.Model.Matches
import androidx.recyclerview.widget.RecyclerView
import com.foxhole.myapplication.Adapter.GenreAdapter.AdapterViewHolder
import com.foxhole.myapplication.Adapter.GenreAdapter.OnGenreClickListener
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.foxhole.myapplication.R
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class GenreAdapter(private val genreList: List<Matches>) :
    RecyclerView.Adapter<AdapterViewHolder>() {
    private var onGenreClickListener: OnGenreClickListener? = null
    fun setItemClickListener(onGenreClickListener: OnGenreClickListener?) {
        this.onGenreClickListener = onGenreClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.genre_item_layout, null)
        return AdapterViewHolder(view, onGenreClickListener)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val singleGenre = genreList[position]
        holder.mTvName.text = singleGenre.name
        holder.mImageView.setImageResource(singleGenre.image)
        holder.mTvDetails.text =
            singleGenre.age + ", " + singleGenre.hgt + ", " + singleGenre.occupation + ", " + singleGenre.place
    }

    fun getGenreAt(position: Int): Matches {
        val genre = genreList[position]
        genre.uid = genreList[position].uid
        return genre
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class AdapterViewHolder(
        itemView: View,
        private val onGenreClickListener: OnGenreClickListener?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mImageView: ImageView
        val mTvName: TextView
        val mTvDetails: TextView
        private val mCardView: ConstraintLayout
        private val mBtnYes: Button
        private val mBtnNo: Button
        override fun onClick(v: View) {
            val position = adapterPosition
            val currentMatch = genreList[position]
            when (v.id) {
                R.id.btnYes -> onGenreClickListener!!.onDelClick(position)
                R.id.btnNo -> onGenreClickListener!!.onDelClick(position)
                R.id.clHomeParent -> onGenreClickListener!!.onGenreClick(currentMatch)
            }
        }

        init {
            mImageView = itemView.findViewById(R.id.iv_profile)
            mTvName = itemView.findViewById(R.id.tvName)
            mTvDetails = itemView.findViewById(R.id.tvDetails)
            mCardView = itemView.findViewById(R.id.clHomeParent)
            mBtnNo = itemView.findViewById(R.id.btnNo)
            mBtnYes = itemView.findViewById(R.id.btnYes)
            mCardView.setOnClickListener(this)
            mBtnYes.setOnClickListener(this)
            mBtnNo.setOnClickListener(this)
        }
    }

    interface OnGenreClickListener {
        fun onGenreClick(genre: Matches?)
        fun onDelClick(pos: Int)
    }
}