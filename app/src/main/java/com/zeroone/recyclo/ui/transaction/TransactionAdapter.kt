package com.zeroone.recyclo.ui.transaction

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItemTransactionAll
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils


class TransactionAdapter(private val vm : TransactionViewModel, private val listGoods: ArrayList<DataItemTransactionAll>, private val context: Context,
                         override val lifecycle: Lifecycle
) : RecyclerView.Adapter<TransactionAdapter.ListViewHolder>(),
    LifecycleOwner {
    private lateinit var loading : LoadingBar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGoods.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val goods = listGoods[position]
        if (goods.recycledItems.size > 0) {


        Glide.with(holder.itemView.context)
            .load(goods.recycledItems[0].recycledItem.image1)
            .centerCrop()
            .into(holder.hero)

        loading = LoadingBar(holder.itemView.context as Activity)

        holder.kode.text= "kode transaksi : ${goods.id}"
        holder.name.text = goods.recycledItems[0].recycledItem.title
        holder.price.text = Utils.formatrupiah(goods.totalPrice.toDouble())
        holder.stok.text = goods.totalAmount.toString()
        holder.barangTotalHarga.text = Utils.formatrupiah(goods.totalPrice.toDouble())
        holder.status.text = goods.statusTransaction.lowercase()
            if (goods.statusTransaction.lowercase().equals("wating")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.GONE
                holder.batal.visibility = View.VISIBLE
            } else if (goods.statusTransaction.lowercase().equals("sending")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.VISIBLE
                holder.batal.visibility = View.GONE
            } else if (goods.statusTransaction.lowercase().equals("done")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.GONE
                holder.batal.visibility = View.GONE
            }

        }else {

            Glide.with(holder.itemView.context)
                .load(goods.image)
                .centerCrop()
                .into(holder.hero)

            loading = LoadingBar(holder.itemView.context as Activity)


            holder.kode.text= "kode transaksi : ${goods.id}"
            holder.name.text = goods.recycledItems[0].recycledItem.title
            holder.price.text = Utils.formatrupiah(goods.totalPrice.toDouble())
            holder.stok.text = goods.totalAmount.toString()
            holder.barangTotalHarga.text = Utils.formatrupiah(goods.totalPrice.toDouble())
            holder.feeTotalHarga.text = Utils.formatrupiah("1000".toDouble())
            holder.status.text = goods.statusTransaction.lowercase()

            if (goods.statusTransaction.lowercase().equals("wating")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.GONE
                holder.batal.visibility = View.VISIBLE
            } else if (goods.statusTransaction.lowercase().equals("sending")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.VISIBLE
                holder.batal.visibility = View.GONE
            } else if (goods.statusTransaction.lowercase().equals("done")) {
                holder.konfirmasi.visibility = View.GONE
                holder.selesai.visibility = View.GONE
                holder.batal.visibility = View.GONE
            }
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title_dashboard)
        var hero: ImageView = itemView.findViewById(R.id.hero)
        var price: TextView = itemView.findViewById(R.id.price_dashboard)
        var stok: TextView = itemView.findViewById(R.id.stok)
        var barangTotalHarga: TextView = itemView.findViewById(R.id.harga_total)
        var feeTotalHarga: TextView = itemView.findViewById(R.id.fee)
        var batal: Button = itemView.findViewById(R.id.batal)
        var selesai: Button = itemView.findViewById(R.id.selesai)
        var konfirmasi: Button = itemView.findViewById(R.id.konfirmasi)
        var status: Button = itemView.findViewById(R.id.status)
        var kode: TextView = itemView.findViewById(R.id.kode)
        var kontakPembeli: TextView = itemView.findViewById(R.id.kontak_pembeli)
        var kontakPenjual: TextView = itemView.findViewById(R.id.kontak_penjual)
    }

}