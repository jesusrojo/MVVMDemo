package com.jesusrojo.mvvmdemo.presentation.activities


import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jesusrojo.mvvmdemo.LeakCanaryActivity
import com.jesusrojo.mvvmdemo.R
import com.jesusrojo.mvvmdemo.databinding.ActivityMainDrawerBinding
import com.jesusrojo.mvvmdemo.util.startMyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDrawerActivity: LeakCanaryActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations. ANDROID STUDIO EXAMPLE
        //appBarConfiguration = AppBarConfiguration(setOf(R.id.FirstFragment, R.id.SecondFragment), drawerLayout )

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.menu_item_drawer_01 -> {
                        startMyActivity<MainActivity>()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}