package com.zeroone.recyclo.ui.longlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityLonglistBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.detail.DetailActivity
import com.zeroone.recyclo.utils.SpacesItemDecoration

class LonglistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLonglistBinding
    private lateinit var vm: LonglistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLonglistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bestSellingProduct1.setOnClickListener {
            startActivity(Intent(this@LonglistActivity,DetailActivity::class.java))
        }

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this,ViewModelFactory(this,pref)).get(LonglistViewModel::class.java)

        val layoutManager = GridLayoutManager(this,2)
        binding.rvGoods.layoutManager = layoutManager
        binding.rvGoods.addItemDecoration(SpacesItemDecoration(20))

        vm.getToken().observe(this){
            vm.getGoods(it)
        }
        vm.goods.observe(this){
            setGoods(it)
        }

        binding.include.back.setOnClickListener{
            finish()
        }
    }

    private fun setGoodsPagging() {
        val adapter = LongListAdapterPagging()
        binding.rvGoods.adapter = adapter
        vm.getAllGoods.observe(this){
            adapter.submitData(lifecycle, it)
        }

    }

    private fun setGoods(data:List<DataItem>) {
        var  arrGoods:ArrayList<DataItem> =  ArrayList()
        for (goods in data) {
            arrGoods.add(goods)
        }
        val adapter = LongListAdapter(this,arrGoods)
        binding.rvGoods.adapter = adapter

    }
}