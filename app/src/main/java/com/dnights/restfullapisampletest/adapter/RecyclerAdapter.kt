package com.dnights.restfullapisampletest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnights.restfullapisampletest.api.data.PhotoData
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.dnights.restfullapisampletest.R
import com.bumptech.glide.Glide

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    private var itemsList = ArrayList<PhotoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imageview, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(itemsList[position])
    }

    fun setList(list: List<PhotoData>){
        itemsList = list as ArrayList<PhotoData>
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageview : ImageView = itemView.findViewById(R.id.imageView_thumb)

        fun onBind(data: PhotoData){
            Glide.with(imageview).load(data.urls.thumb).into(imageview)
        }
    }
}