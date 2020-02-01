package com.architecture.study.data.repository.di

import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<TickerRepository>(named(Exchange.BITHUMB.exchangeName)) { BithumbRepository(get()) }
    single<TickerRepository>(named(Exchange.UPBIT.exchangeName)) { UpbitRepository(get()) }
    single<TickerRepository>(named(Exchange.COINONE.exchangeName)) { CoinoneRepository(get()) }

    single<ExchangeRepository> {
        ExchangeRepositoryImpl(
            mapOf(
                Exchange.UPBIT.exchangeName to get(named(Exchange.UPBIT.exchangeName)),
                Exchange.BITHUMB.exchangeName to get(named(Exchange.BITHUMB.exchangeName)),
                Exchange.COINONE.exchangeName to get(named(Exchange.COINONE.exchangeName))
            )
        )
    }
}