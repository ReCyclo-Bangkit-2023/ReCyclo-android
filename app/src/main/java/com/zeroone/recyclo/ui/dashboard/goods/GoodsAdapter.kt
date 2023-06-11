package com.zeroone.recyclo.ui.dashboard.goods

import android.content.Context
import android.content.Intent
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


class GoodsAdapter(private val context : Context, private val listGoods: ArrayList<DataItem>) : RecyclerView.Adapter<GoodsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGoods.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val goods = listGoods[position]
        Glide.with(holder.itemView.context)
            .load(goods.image1)
            .centerCrop()
            .into(holder.hero)

        holder.delete.setOnClickListener {

        }
        holder.name.text = goods.title
        holder.price.text = goods.price
        holder.stok.text = goods.amount
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title_dashboard)
        var hero: ImageView = itemView.findViewById(R.id.hero)
        var price: TextView = itemView.findViewById(R.id.price_dashboard)
        var stok: TextView = itemView.findViewById(R.id.stok)
        var delete: View = itemView.findViewById(R.id.delete)
    }
}