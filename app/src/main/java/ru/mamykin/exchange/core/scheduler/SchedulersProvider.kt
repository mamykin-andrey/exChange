package ru.mamykin.exchange.core.scheduler

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun mainThread(): Scheduler
}