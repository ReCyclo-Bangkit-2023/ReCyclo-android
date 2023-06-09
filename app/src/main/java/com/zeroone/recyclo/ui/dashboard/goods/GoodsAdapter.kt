package com.zeroone.recyclo.ui.dashboard.goods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem


class GoodsAdapter :  PagingDataAdapter<DataItem, GoodsAdapter.ListViewHolder>(
    DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val goods = getItem(position)
        if (goods != null) {
            Glide.with(holder.itemView.context)
                .load(goods.image1)
                .centerCrop()

                .into(holder.hero)
        }

        holder.name.text = goods?.title



        holder.price.text = goods?.price
        holder.stok.text = goods?.amount
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title_dashboard)
        var hero: ImageView = itemView.findViewById(R.id.hero)
        var price: TextView = itemView.findViewById(R.id.price_dashboard)
        var stok: TextView = itemView.findViewById(R.id.stok)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}