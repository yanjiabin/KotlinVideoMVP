package com.hot.kotlinvideomvp.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 *  author : anderson
 *  date   : 2020/8/31
 *  desc   :
 */
class TabEntity(var title:String,var selectedIcon:Int,var unSelectedIcon:Int) :CustomTabEntity{

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon;
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon;
    }

    override fun getTabTitle(): String {
        return title;
    }

}