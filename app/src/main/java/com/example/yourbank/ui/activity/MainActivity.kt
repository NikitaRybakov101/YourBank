package com.example.yourbank.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yourbank.R
import com.example.yourbank.ui.fragments.MainSearchBinFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
          //  .setCustomAnimations(R.anim.anim_layout_2, R.anim.anim_layout)
            .replace(R.id.container, MainSearchBinFragment.newInstance())
            .commit()
    }
}