package com.example.retromarket.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        selectBottomItem(R.id.nav_cart)
    }
}