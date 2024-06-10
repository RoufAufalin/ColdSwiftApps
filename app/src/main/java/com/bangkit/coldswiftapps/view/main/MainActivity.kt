package com.bangkit.coldswiftapps.view.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bangkit.coldswiftapps.R
import com.bangkit.coldswiftapps.databinding.ActivityMainBinding
import com.bangkit.coldswiftapps.view.home.HomeFragment
import com.bangkit.coldswiftapps.view.myprofile.MyProfile
import com.bangkit.coldswiftapps.view.myticket.MyTicket
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        installSplashScreen()
        setContentView(binding.root)

        drawer = binding.drawerLayout
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val togle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)

        drawer.addDrawerListener(togle)
        togle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        togle.syncState()

        if (savedInstanceState == null) {
            replacedFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.nav_home)
        }


    }

    private fun replacedFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> replacedFragment(HomeFragment())
            R.id.nav_myticket -> replacedFragment(MyTicket())
            R.id.nav_myprofile -> replacedFragment(MyProfile())
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}