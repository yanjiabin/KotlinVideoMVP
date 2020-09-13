package com.hot.kotlinvideomvp.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by anderson
 * on 2020/9/3.
 * desc:
 */
class IoMainScheduler<T> :BaseScheduler<T>(Schedulers.io(),AndroidSchedulers.mainThread()) {
}