package com.hot.kotlinvideomvp.views.recycleview

interface MultipleType<in T> {

    fun getLayoutId(item: T, position: Int): Int

}