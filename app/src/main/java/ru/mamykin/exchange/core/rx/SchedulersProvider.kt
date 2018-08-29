package ru.mamykin.exchange.core.rx

import io.reactivex.Scheduler

/**
 * Interface for providing RX Schedulers, used for instant run RX code in tests
 */
interface SchedulersProvider {

    fun io(): Scheduler

    fun mainThread(): Scheduler
}