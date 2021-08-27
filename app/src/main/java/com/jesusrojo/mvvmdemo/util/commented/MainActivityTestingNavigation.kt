@file:Suppress("PackageDirectoryMismatch")
//package com.jesusrojo.mvvmdemo.util.commented
//import android.os.Bundle
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.navigateUp
//import androidx.navigation.ui.setupActionBarWithNavController
//import android.view.View
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.widget.Toolbar
//import androidx.navigation.findNavController
//import androidx.navigation.ui.setupWithNavController
//import com.jesusrojo.mvvmdemo.LeakCanaryActivity
//import com.jesusrojo.mvvmdemo.R
//import com.jesusrojo.mvvmdemo.databinding.ActivityMainBinding
//import com.jesusrojo.mvvmdemo.util.DebugHelp
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivityTestingNavigation : LeakCanaryActivity() {
//
//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
////        initNavControllerWithDrawerAsTodos()
////        initNavControllerWithFragmentContainerView() //2
//        initNavControllerWithFragment() // 1 WithFragment, used with  <fragment  in activity_main.xml
//    }
//
////    private fun initNavControllerWithDrawerAsTodos() {
////        setSupportActionBar(binding.toolbar)
////        val navController: NavController = findNavController(R.id.nav_host_fragment)
////        val drawerLayout = binding.drawerLayout
////        appBarConfiguration = AppBarConfiguration.Builder(R.id.FirstFragment)
////            .setOpenableLayout(drawerLayout)
////            .build()
////        setupActionBarWithNavController(navController, appBarConfiguration)
////        binding.navView.setupWithNavController(navController)
////    }
//
//    // 2
////    private fun initNavControllerWithFragmentContainerView() {
////        setSupportActionBar(binding.toolbar)
////        val navHostFragment: NavHostFragment = supportFragmentManager
////            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
////        val navController: NavController = navHostFragment.navController
////
////        val appBarConfiguration = AppBarConfiguration.Builder(R.id.FirstFragment)
////            .setOpenableLayout(binding.drawerLayout) // DRAWER
////            .build()
////        setupActionBarWithNavController(navController, appBarConfiguration)
////        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
////        binding.navView.setupWithNavController(navController) // DRAWER
////    }
//
//    //////// 1 WithFragment, used with  <fragment  in activity_main.xml
//    private fun initNavControllerWithFragment() {
//        setSupportActionBar(binding.toolbar)
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
////        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
//        setupActionBarWithNavController(navController, appBarConfiguration)
////        binding.navView.setupWithNavController(navController) // DRAWER
//    }
///////////
//
//    override fun onSupportNavigateUp(): Boolean {
//        //1 WithFragment, used with  <fragment  in activity_main.xml
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//
//        //2 WithFragmentContainerView
////        val navHostFragment: NavHostFragment = supportFragmentManager
////            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
////        val navController: NavController = navHostFragment.navController
//
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
//
//    //3
////    override fun onSupportNavigateUp(): Boolean {
////        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
////                || super.onSupportNavigateUp()
////    }
//    // FAB BUTTON
//    fun hideFabBtn() {
//        binding.fab.visibility = View.GONE
//    }
//
//    fun showFabBtn() {
//        binding.fab.visibility = View.VISIBLE
//        binding.fab.setOnClickListener { showAlertDialog( DebugHelp.fullLog) }
//    }
//
//    // MENU TOP RIGHT - SECOND FRAGMENT
//    fun initMenuOptionsSecondFragment() = binding.toolbar.menu.clear()
//
//    // MENU TOP RIGHT - MAIN FRAGMENT
//    fun initMenuOptionsFirstFragment() {
//        binding.toolbar.menu.clear()
//
//        binding.toolbar.inflateMenu(R.menu.menu_top_right)
//
//        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.menu_item_1 -> {
//                   // clearTextViewLogInMainFragment()
//                    return@OnMenuItemClickListener true
//                }
//                R.id.menu_item_2 -> {
//                    // showDetailsDialogFragment()
//                    return@OnMenuItemClickListener true
//                }
//                R.id.menu_item_3 -> {
//                    //  navigateToThirdFragment()
//                    return@OnMenuItemClickListener true
//                }
//            }
//            return@OnMenuItemClickListener false
//        })
//    }
//
//// MENU TOP RIGHT - THIRD FRAGMENT
////    fun initMenuOptionsThirdFragment() {
////        binding.toolbar.menu.clear()
////
////        binding.toolbar.inflateMenu(R.menu.menu_top_right_third)
////
////        binding.toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
////            when (item.itemId) {
////                R.id.menu_item_1 -> {
////                    onClickMenuTopRightThirdFragmentItem1()
////                    return@OnMenuItemClickListener true
////                }
////            }
////            return@OnMenuItemClickListener false
////        })
////    }
////
////    // TO THIRD FRAGMENT
////    private fun onClickMenuTopRightThirdFragmentItem1() {
////        val navHostFragment: NavHostFragment =
////            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
////
////        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
////
////        val thirdFragment: ThirdFragment = currentFragment as ThirdFragment
////
////        thirdFragment.onClickMenuTopRightThirdFragmentItem1()
////    }
//
//// TO MAIN FRAGMENT
////private fun clearTextViewLogInMainFragment() {
////    val navHostFragment: NavHostFragment =
////        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
////
////    val currentFragment = navHostFragment.childFragmentManager.fragments[0]
////
////    val firstFragment: FirstFragment = currentFragment as FirstFragment
////
////    firstFragment.clearTextViewLog()
////}
//
//
//////////MENU TOP RIGHT IN ACTIVITY
////    override fun onCreateOptionsMenu(menu: Menu): Boolean {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        menuInflater.inflate(R.menu.menu_main, menu)
////        return true
////    }
////
////    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        return when (item.itemId) {
////            R.id.action_settings -> true
////            else -> super.onOptionsItemSelected(item)
////        }
////    }
////////////
//
//    private fun showAlertDialog(message: String) {
//        val builder = AlertDialog.Builder(applicationContext)
//        builder.setMessage(message)
//        builder.setPositiveButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
//        builder.create().show()
//    }
//}