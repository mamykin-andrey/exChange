package ru.mamykin.exchange.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mamykin.exchange.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_main)
    }
}