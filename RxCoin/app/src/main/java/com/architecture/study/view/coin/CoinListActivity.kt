package com.architecture.study.view.coin

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.architecture.study.R
import com.architecture.study.base.BaseActivity
import com.architecture.study.databinding.ActivityCoinBinding
import com.google.android.material.tabs.TabLayout


class CoinListActivity : BaseActivity<ActivityCoinBinding>(R.layout.activity_coin) {

    private val tabList = listOf(
        R.string.monetary_unit_1,
        R.string.monetary_unit_2,
        R.string.monetary_unit_4
    )

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.run {
            tabLayoutMonetaryUnit.setupWithViewPager(viewPagerCoinList)

            viewPagerCoinList.run {
                offscreenPageLimit = 3
                adapter = object : FragmentPagerAdapter(
                    supportFragmentManager,
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ) {
                    override fun getItem(position: Int): Fragment =
                        CoinListFragment.newInstance(getString(tabList[position]))

                    override fun getCount(): Int =
                        tabList.size

                    override fun getPageTitle(position: Int): CharSequence? =
                        getString(tabList[position])
                }
                addOnPageChangeListener(
                    TabLayout.TabLayoutOnPageChangeListener(tabLayoutMonetaryUnit)
                )
            }
        }
    }
}
