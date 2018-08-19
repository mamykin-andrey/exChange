package ru.mamykin.exchange.presentation.scheduler

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun mainThread(): Scheduler
}