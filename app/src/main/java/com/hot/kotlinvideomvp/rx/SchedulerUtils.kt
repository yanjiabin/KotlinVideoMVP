package com.hot.kotlinvideomvp.rx

/**
 * Created by anderson
 * on 2020/9/3.
 * desc:
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}