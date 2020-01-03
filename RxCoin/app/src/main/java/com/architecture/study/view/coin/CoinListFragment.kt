package com.architecture.study.view.coin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.architecture.study.BR
import com.architecture.study.R
import com.architecture.study.base.BaseAdapter
import com.architecture.study.base.BaseFragment
import com.architecture.study.data.model.Ticker
import com.architecture.study.databinding.FragmentCoinlistBinding
import com.architecture.study.databinding.ItemTickerBinding
import com.architecture.study.viewmodel.TickerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CoinListFragment : BaseFragment<FragmentCoinlistBinding>(R.layout.fragment_coinlist) {

    private val tickerViewModel by viewModel<TickerViewModel> {
        parametersOf(arguments?.getString(BASE_CURRENCY))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            tickerVM = tickerViewModel
            recyclerViewCoinList.adapter =
                object : BaseAdapter<Ticker, ItemTickerBinding>(R.layout.item_ticker, BR.ticker) {}
        }

        tickerViewModel.exceptionMessage
            .observe(this@CoinListFragment, Observer {
                showMessage(it)
            })

        tickerViewModel.getTickerList()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val BASE_CURRENCY = "base_currency"

        fun newInstance(currency: String) = CoinListFragment().apply {
            arguments = Bundle().apply {
                putString(BASE_CURRENCY, currency)
            }
        }
    }
}