package com.hot.kotlinvideomvp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.hot.kotlinvideomvp.utils.DisplayManager
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * Created by anderson
 * on 2020/9/3.
 * desc:
 */
class MyApplication :MultiDexApplication() {

    private var refWatcher: RefWatcher? = null
        companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }

    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
        MultiDex.install(context)
    }
}