package ru.mamykin.exchange

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaPlugins
import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.TestScheduler

class TestSchedulerRule : TestRule {

    val testScheduler = TestScheduler()

    override fun apply(base: Statement, d: Description): Statement {

        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.getInstance().registerSchedulersHook(object : RxJavaSchedulersHook() {
                    override fun getIOScheduler(): Scheduler = testScheduler
                })
                RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
                    override fun getMainThreadScheduler(): Scheduler = testScheduler
                })
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.getInstance().reset()
                    RxAndroidPlugins.getInstance().reset()
                }
            }
        }
    }
}