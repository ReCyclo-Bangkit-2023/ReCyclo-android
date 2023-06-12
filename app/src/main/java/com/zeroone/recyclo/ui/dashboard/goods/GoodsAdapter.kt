package com.zeroone.recyclo.ui.dashboard.goods

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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.dashboard.DashboardViewModel
import com.zeroone.recyclo.ui.dashboard.ViewModelFactory
import com.zeroone.recyclo.ui.dashboard.goods.edit.EditActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils


class GoodsAdapter(private val vm : GoodsViewModel,private val lifecycleOwner: LifecycleOwner , private val listGoods: ArrayList<DataItem>) : RecyclerView.Adapter<GoodsAdapter.ListViewHolder>() {
    private lateinit var loading : LoadingBar
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

        loading = LoadingBar(holder.itemView.context as Activity)

        holder.delete.setOnClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_delete_confirmation)
            val yesBtn = dialog.findViewById(R.id.yes) as Button
            val noBtn = dialog.findViewById(R.id.no) as Button
            yesBtn.setOnClickListener {
                dialog.dismiss()
                loading.startLoading()
                vm.getToken().observe(lifecycleOwner){

                    vm.delete(it,goods.id)
                }

                vm.status.observe(lifecycleOwner){
                    if (it){
                        (holder.itemView.context as Activity).finish()
                        holder.itemView.context.startActivity(Intent(holder.itemView.context,DashboardActivity::class.java))
                    }
                }
            }
            noBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        holder.edit.setOnClickListener {
            val intent = Intent(holder.itemView.context,EditActivity::class.java)
            intent.putExtra("goods",goods)
            holder.itemView.context.startActivity(intent)
        }
        holder.name.text = goods.title
        holder.price.text = Utils.formatrupiah(goods.price.toDouble())
        holder.stok.text = goods.amount
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title_dashboard)
        var hero: ImageView = itemView.findViewById(R.id.hero)
        var price: TextView = itemView.findViewById(R.id.price_dashboard)
        var stok: TextView = itemView.findViewById(R.id.stok)
        var delete: View = itemView.findViewById(R.id.delete)
        var edit: View = itemView.findViewById(R.id.edit)
    }

}