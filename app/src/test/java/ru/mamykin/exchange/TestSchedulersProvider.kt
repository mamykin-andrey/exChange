package ru.mamykin.exchange

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.presentation.scheduler.SchedulersProvider

class TestSchedulersProvider : SchedulersProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun mainThread(): Scheduler = Schedulers.trampoline()
}