package ru.mamykin.exchange.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseActivity
import ru.mamykin.exchange.core.platform.UiUtils
import ru.mamykin.exchange.ui.fragment.MainFragment

/**
 * Activity, which hosts all fragments in app
 */
class RootActivity : BaseActivity() {

    companion object {
        fun newInstance(context: Context): Intent =
                Intent(context, RootActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        UiUtils.addFragment(supportFragmentManager,
                R.id.flContainer, MainFragment.newInstance(), false)
    }
}