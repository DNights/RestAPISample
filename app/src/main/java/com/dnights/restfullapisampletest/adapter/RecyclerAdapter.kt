package com.dnights.restfullapisampletest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnights.restfullapisampletest.api.data.PhotoData
import android.view.LayoutInflater
import android.view.View
import com.dnights.restfullapisampletest.R
import com.bumptech.glide.Glide
import java.lang.Exception
import kotlin.collections.ArrayList


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private val HEADER_SIZE = 1
    private val FOOTER_SIZE = 1

    private var itemsList = ArrayList<PhotoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when(viewType){
            TYPE_HEADER ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }

            TYPE_FOOTER-> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
                FooterViewHolder(view)
            }

            TYPE_ITEM->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imageview, parent, false)
                ItemViewHolder(view)
            }

            else -> throw Exception("Unknow viewType $viewType")
        }
    
    override fun getItemCount() = itemsList.size + HEADER_SIZE + FOOTER_SIZE

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.onBind(itemsList[position - HEADER_SIZE])
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(position) {
            0 -> TYPE_HEADER
            (itemsList.size + HEADER_SIZE) -> TYPE_FOOTER
            else -> TYPE_ITEM
        }

    fun setList(list: List<PhotoData>){
        itemsList = list as ArrayList<PhotoData>
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView)

    class FooterViewHolder(itemView: View) : BaseViewHolder(itemView)

    class ItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun onBind(data: PhotoData){
            Glide
                .with(itemView)
                .load(data.urls.thumb)
                .into(itemView.findViewById(R.id.imageView_thumb))
        }
    }
}