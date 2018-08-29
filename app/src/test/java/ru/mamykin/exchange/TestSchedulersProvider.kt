package ru.mamykin.exchange

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.core.rx.SchedulersProvider

class TestSchedulersProvider : SchedulersProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun mainThread(): Scheduler = Schedulers.trampoline()
}