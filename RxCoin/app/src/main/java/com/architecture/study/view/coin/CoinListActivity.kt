package com.architecture.study.view.coin

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.architecture.study.R
import com.architecture.study.base.BaseActivity
import com.architecture.study.data.enums.Exchange
import com.architecture.study.databinding.ActivityCoinBinding
import com.architecture.study.util.PrefUtil

class CoinListActivity : BaseActivity<ActivityCoinBinding>(R.layout.activity_coin) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        binding.rbUpbit.isChecked = true
    }
    private fun initView(){
        binding.run {
            rgCurrencySelector.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    rbUpbit.id -> {
                        setCurrentExchange(Exchange.UPBIT.exchangeName)
                        setupTab(Exchange.UPBIT.baseCurrencies)
                    }
                    rbBithumb.id -> {
                        setCurrentExchange(Exchange.BITHUMB.exchangeName)
                        setupTab(Exchange.BITHUMB.baseCurrencies)
                    }
                }
            }
        }
    }

    private fun setupTab(tabList: List<String>) {
        binding.run {
            tlMonetaryUnit.setupWithViewPager(vpCoinList)

            vpCoinList.run {
                offscreenPageLimit = 1
                adapter = object : FragmentPagerAdapter(
                    supportFragmentManager,
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ) {
                    override fun getItem(position: Int): Fragment =
                        CoinListFragment.newInstance(tabList[position])

                    override fun getCount(): Int =
                        tabList.size

                    override fun getPageTitle(position: Int): CharSequence? =
                        tabList[position]
                }
            }
        }
    }

    private fun setCurrentExchange(currentExchange: String) {
        PrefUtil.setStrValue(
            applicationContext,
            CURRENT_EXCHANGE,
            currentExchange
        )
    }

    companion object {
        const val CURRENT_EXCHANGE = "current_exchange"
    }

}
