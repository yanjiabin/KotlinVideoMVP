package com.hot.kotlinvideomvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.ui.fragment.HomeFragment
import com.hot.kotlinvideomvp.views.MultipleStatusView

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
open abstract class BaseFragment : Fragment() {


    /**
     *视图是否加载完毕
     */
    private var isViewPrepare: Boolean = false

    /**
     *数据是否已经加载过
     */
    private var hasLoadData: Boolean = false

    /**
     * 多种状态的View的切换
     */
    open var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(),null)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()

        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    abstract fun initView()

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    @LayoutRes
    abstract fun getLayout(): Int

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()
        activity?.let { MyApplication.getRefWatcher(it)?.watch(it) }
    }
}