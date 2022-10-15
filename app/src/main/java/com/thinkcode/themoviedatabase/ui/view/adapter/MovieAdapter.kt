package com.thinkcode.themoviedatabase.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thinkcode.themoviedatabase.R

class MovieAdapter(val list:List<com.thinkcode.themoviedatabase.data.model.Result>): RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutinflater=LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutinflater.inflate(R.layout.movie_item,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item=list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}