package com.zeroone.recyclo.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zeroone.recyclo.ui.dashboard.goods.GoodsFragment
import com.zeroone.recyclo.ui.dashboard.waste.WasteFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var user_id: Int = 0
    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = GoodsFragment()
            1 -> fragment = WasteFragment()
        }
        fragment?.arguments = Bundle().apply {
            putInt("position", position + 1)
            putInt("user_id", user_id)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}