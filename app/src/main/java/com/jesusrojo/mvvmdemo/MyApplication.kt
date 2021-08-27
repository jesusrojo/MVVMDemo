package com.jesusrojo.mvvmdemo

import androidx.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : MultiDexApplication() { // MULTIDEX

    private var mRefWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            setupTimber()
            setupLeakCanary()
        }
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    //LEAK CANARY
    private fun setupLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this)
        }
    }

    fun mustDieLeakCanary(`object`: Any?) {
        mRefWatcher?.watch(`object`)
    }

///////// todo bug in Github Actions, error:
//    > Task :app:compileDebugUnitTestJavaWithJavac NO-SOURCE
//    > Task :app:testDebugUnitTest
//    Type mismatch: inferred type is Any? but Any was expected
//    TESTING WITH
//    fun mustDieLeakCanary(any: Any?) {
//        mRefWatcher?.watch(`object`)
//    }
/////////
}