package com.example.retromarket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.example.retromarket.databinding.ActivityBaseBinding
import com.example.retromarket.service.SessionManager
import com.example.retromarket.ui.CartActivity
import com.example.retromarket.ui.LoginActivity
import com.example.retromarket.MainActivity

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityBaseBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottomBar: BottomNavigationView // Déclare bottomBar au niveau de la classe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomBar = binding.bottomBar // Initialise bottomBar ici
        drawerLayout = binding.drawerLayout
        val navigationView = binding.navView

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        updateNavigationMenuVisibility()
        updateUserProfileName()
    }

    override fun onStart() {
        super.onStart()
        bottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is MainActivity) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    true
                }
                R.id.nav_cart -> {
                    if (this !is CartActivity) {
                        startActivity(Intent(this, CartActivity::class.java))
                    }
                    true
                }
                R.id.nav_menu -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START)
                    }
                    true
                }
                else -> false
            }
        }
    }

    fun selectBottomItem(itemId: Int) {
        bottomBar.selectedItemId = itemId
    }

    override fun setContentView(layoutResID: Int) {
        LayoutInflater.from(this).inflate(layoutResID, binding.mainContent, true)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.logout -> {
                SessionManager.saveAuthToken(this, null)
                updateNavigationMenuVisibility()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.myCart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
            // Ajoutez d'autres cas pour les autres éléments de menu si nécessaire
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    open fun handleNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }

    private fun updateNavigationMenuVisibility() {
        val navigationView = binding.navView
        val menu = navigationView.menu
        val isLoggedIn = SessionManager.fetchAuthToken(this) != null

        menu.findItem(R.id.login).isVisible = !isLoggedIn
        menu.findItem(R.id.profile).isVisible = isLoggedIn
        menu.findItem(R.id.logout).isVisible = isLoggedIn
        menu.findItem(R.id.myProduct).isVisible = isLoggedIn
        menu.findItem(R.id.myOrder).isVisible = isLoggedIn
        menu.findItem(R.id.myCart).isVisible = isLoggedIn
    }

    private fun updateUserProfileName() {
        val navigationView = binding.navView
        val headerView = navigationView.getHeaderView(0)
        val profileNameTextView = headerView.findViewById<TextView>(R.id.profileName)
        val user = SessionManager.fetchUser(this)

        if (user != null) {
            profileNameTextView.text = "${user.prenom} ${user.nom} (${user.username})"
        } else {
            profileNameTextView.text = "Non connecté"
        }
    }
}
