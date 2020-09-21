package com.hot.kotlinvideomvp.ui.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.model.bean.TabEntity
import com.hot.kotlinvideomvp.ui.fragment.DiscoveryFragment
import com.hot.kotlinvideomvp.ui.fragment.HomeFragment
import com.hot.kotlinvideomvp.ui.fragment.HotFragment
import com.hot.kotlinvideomvp.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    @LayoutRes
    override fun getLayout(): Int =R.layout.activity_main
    private var mIndex: Int = 0
    private val mIconUnSelectIds =  intArrayOf(R.mipmap.ic_home_normal,R.mipmap.ic_discovery_normal,R.mipmap.ic_hot_normal,R.mipmap.ic_mine_normal)

    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected,R.mipmap.ic_discovery_selected,R.mipmap.ic_hot_selected,R.mipmap.ic_mine_selected)

    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun start() {

    }

    override fun initView() {
    }

    private fun switchFragment(position: Int) {
        val transaction =supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mHomeFragment?.let{
                transaction.show(it)
            }?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment = it
                transaction.add(R.id.fl_container,it,"home")
            }
            1 -> mDiscoveryFragment?.let {
                transaction.show(it)
            }?:DiscoveryFragment.getInstance(mTitles[position]).let {
                mDiscoveryFragment = it
                transaction.add(R.id.fl_container,it,"discovery")
            }
            2 -> mHotFragment?.let {
                transaction.show(it)
            }?:HotFragment.getInstance(mTitles[position]).let {
                mHotFragment = it
                transaction.add(R.id.fl_container,it,"hot")
            }
            3 -> mMineFragment?.let {
                transaction.show(it)
            }?:MineFragment.getInstance(mTitles[position]).let {
                mMineFragment = it
                transaction.add(R.id.fl_container,it,"mine")
            }

            else->{

            }
        }
        mIndex = position
        tab_layout.currentTab = position
        transaction.commitAllowingStateLoss()

    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let{transaction.hide(it)}
        mDiscoveryFragment?.let{transaction.hide(it)}
        mHotFragment?.let{transaction.hide(it)}
        mMineFragment?.let{transaction.hide(it)}

    }

    override fun initData() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab =mIndex
        switchFragment(mIndex)
    }

    private fun initTab() {
        (mTitles.indices).mapTo(mTabEntities){
            TabEntity(mTitles[it],mIconSelectIds[it],mIconUnSelectIds[it])
        }

        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                //切换fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

}
