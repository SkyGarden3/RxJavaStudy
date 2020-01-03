package com.architecture.study.data.repository.di

import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.repository.BithumbRepository
import com.architecture.study.data.repository.ExchangeRepository
import com.architecture.study.data.repository.TickerRepository
import com.architecture.study.data.repository.UpbitRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<TickerRepository>(named(Exchange.BITHUMB.exchangeName)) { BithumbRepository(get()) }
    single<TickerRepository>(named(Exchange.UPBIT.exchangeName)) { UpbitRepository(get()) }

    single {
        ExchangeRepository(
            mapOf(
                Exchange.UPBIT.exchangeName to get(named(Exchange.UPBIT.exchangeName)),
                Exchange.BITHUMB.exchangeName to get(named(Exchange.BITHUMB.exchangeName))
            )
        )
    }
}