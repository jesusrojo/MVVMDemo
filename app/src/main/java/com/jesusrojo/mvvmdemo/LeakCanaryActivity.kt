package com.jesusrojo.mvvmdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//@AndroidEntryPoint //TODO use or not?
abstract class LeakCanaryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        todo debug
        Toast.makeText(applicationContext, javaClass.simpleName, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        destroyLeekCanary()
        super.onDestroy()
    }

    // LEAK CANARY
    private fun destroyLeekCanary() {
        val app = application as MyApplication
        app.mustDieLeakCanary(this)
    }
}