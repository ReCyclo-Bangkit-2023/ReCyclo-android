package com.zeroone.recyclo.ui.cart

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.DataItemCart
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.dashboard.DashboardViewModel
import com.zeroone.recyclo.ui.dashboard.ViewModelFactory
import com.zeroone.recyclo.ui.dashboard.goods.edit.EditActivity
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils


class CartAdapter(private val vm : CartViewModel, private val listGoods: ArrayList<DataItemCart>, private val context: Context,
                  override val lifecycle: Lifecycle
) : RecyclerView.Adapter<CartAdapter.ListViewHolder>(),
    LifecycleOwner {
    private lateinit var loading : LoadingBar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGoods.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val goods = listGoods[position]
        Glide.with(holder.itemView.context)
            .load(goods.image)
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
                vm.getToken().observe(this){
                    vm.deleteCart(it,goods.id)
                }
                vm.status.observe(this){
                    if (it) {
                        (holder.itemView.context as Activity).finish()
                        holder.itemView.context.startActivity(Intent(holder.itemView.context,CartActivity::class.java))
                    }
                }


                dialog.dismiss()

            }
            noBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        holder.minus.setOnClickListener {
            vm.getToken().observe(this){
                vm.minCart(it,goods.id)
            }
            vm.status.observe(this){
                (holder.itemView.context as Activity).finish()
                holder.itemView.context.startActivity(Intent(holder.itemView.context,CartActivity::class.java))
            }
        }
        holder.plus.setOnClickListener {
            vm.getToken().observe(this){
                vm.plusCart(it,goods.id)
            }
            vm.status.observe(this){
                (holder.itemView.context as Activity).finish()
                holder.itemView.context.startActivity(Intent(holder.itemView.context,CartActivity::class.java))
            }
        }
        holder.name.text = goods.title
        holder.price.text = Utils.formatrupiah(goods.price.toDouble())
        holder.stok.text = goods.amount.toString()
        holder.selected_amount.text = goods.amount.toString()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title_cart)
        var selected_amount: TextView = itemView.findViewById(R.id.selected_cart)
        var hero: ImageView = itemView.findViewById(R.id.hero)
        var price: TextView = itemView.findViewById(R.id.price_cart)
        var stok: TextView = itemView.findViewById(R.id.stok)
        var delete: View = itemView.findViewById(R.id.delete_cart)
        var plus: View = itemView.findViewById(R.id.plus)
        var minus: View = itemView.findViewById(R.id.minus)
    }

}