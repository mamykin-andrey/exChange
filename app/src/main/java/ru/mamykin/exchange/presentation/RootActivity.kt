package ru.mamykin.exchange.presentation

import android.os.Bundle
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseActivity
import ru.mamykin.exchange.presentation.main.MainFragment

class RootActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        addFragment(R.id.flContainer, MainFragment.newInstance(), false)
    }
}