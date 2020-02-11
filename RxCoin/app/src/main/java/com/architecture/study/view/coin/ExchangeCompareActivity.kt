package com.architecture.study.view.coin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.architecture.study.BR
import com.architecture.study.R
import com.architecture.study.base.BaseActivity
import com.architecture.study.base.BaseAdapter
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.databinding.ActivityExchangeCompareBinding
import com.architecture.study.databinding.ItemCompareTickerBinding
import com.architecture.study.view.coin.model.CompareTickerItem
import com.architecture.study.viewmodel.ExchangeCompareViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeCompareActivity :
    BaseActivity<ActivityExchangeCompareBinding>(R.layout.activity_exchange_compare) {

    private val viewModel by viewModel<ExchangeCompareViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            vm = viewModel
            rvExchangeCompare.adapter =
                object : BaseAdapter<CompareTicker, ItemCompareTickerBinding>(
                    R.layout.item_compare_ticker,
                    BR.item
                ) {}

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
        val clickedTicker = intent.getParcelableExtra<CompareTickerItem>(CLICKED_TICKER)

        binding.tvTitle.text = clickedTicker.coinName + "/" + clickedTicker.baseCurrency
        viewModel.getCompareTickerList(clickedTicker)

        viewModel.exceptionMessage.observe(this, Observer {
            it.getContentIfNotHandled()?.let {message->
                showMessage(message)
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val CLICKED_TICKER = "clicked_ticker"
    }
}