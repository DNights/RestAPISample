package com.dnights.restfullapisampletest.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnights.restfullapisampletest.R
import com.dnights.restfullapisampletest.api.data.PhotoData
import java.lang.Exception

class PhotoPageAdepter(): PagedListAdapter<PhotoData, PhotoPageAdepter.BaseViewHolder>(DIFF_CALLBACK){

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PhotoData>() {
            override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PhotoData, newItem: PhotoData) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imageview, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.onBind(getItem(position)!!)
        }
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun onBind(data: PhotoData){
            Glide
                .with(itemView)
                .load(data.urls.thumb)
                .into(itemView.findViewById(R.id.imageView_thumb))
        }
    }

}