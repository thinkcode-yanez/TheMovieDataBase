package com.thinkcode.themoviedatabase.ui.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.thinkcode.themoviedatabase.core.Constants
import com.thinkcode.themoviedatabase.data.model.Result
import com.thinkcode.themoviedatabase.databinding.MovieItemBinding
import com.thinkcode.themoviedatabase.ui.view.DetailsActivity


class PageAdapter : PagingDataAdapter<Result, PageAdapter.MyViewHolder>(diffCallBack) {

    inner class MyViewHolder(val binding: MovieItemBinding) :
        ViewHolder(binding.root)

    companion object {

        val diffCallBack = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }


        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem=getItem(position)
        //this get item is from PageDataAdapter
        holder.binding.apply {
            tvtitleMovie.text=currentItem?.original_title
            val posterpath= Constants.PHOTO_BASE_URL+currentItem?.poster_path
            Glide.with(imageView.context).load(posterpath).into(imageView)
            imageView.setOnClickListener {
                val intent= Intent(imageView.context, DetailsActivity::class.java)
                intent.putExtra("ID",currentItem!!.id)
                //intent.putExtra("ide","45")
                imageView.context.startActivity(intent)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

}