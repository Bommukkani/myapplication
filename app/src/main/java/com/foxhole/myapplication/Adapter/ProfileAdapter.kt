package com.foxhole.myapplication.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.foxhole.myapplication.Model.Movie
import com.foxhole.myapplication.R

class ProfileAdapter(context: Context, private val genreList: List<Movie>) : BaseAdapter() {
    private val mInflator: LayoutInflater

    init {
        this.mInflator = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return genreList.size
    }

    override fun getItem(position: Int): Any {
        return genreList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.single_movie_layout, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.setImageResource(genreList[position].image)
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val label: ImageView

    init {
        this.label = row?.findViewById(R.id.iv_profile) as ImageView
    }
}