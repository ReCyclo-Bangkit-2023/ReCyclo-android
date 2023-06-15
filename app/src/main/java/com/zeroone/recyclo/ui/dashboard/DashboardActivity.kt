package com.zeroone.recyclo.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityDashboardBinding
import com.zeroone.recyclo.ui.cart.CartActivity
import com.zeroone.recyclo.ui.home.HomeActivity
import com.zeroone.recyclo.ui.transaction.TransactionActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val usernameExtra = intent.getStringExtra("user") ?: ""
        sectionsPagerAdapter.user_id = 1

        binding.bottom.cartMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@DashboardActivity, CartActivity::class.java))
        }

        binding.bottom.dashboardMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@DashboardActivity, DashboardActivity::class.java))
        }

        binding.bottom.transactionMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@DashboardActivity, TransactionActivity::class.java))
        }

        binding.bottom.homeMenu.setOnClickListener{
            finish()
            startActivity(Intent(this@DashboardActivity, HomeActivity::class.java))
        }

        binding.include.back.setOnClickListener{
            finish()
        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}