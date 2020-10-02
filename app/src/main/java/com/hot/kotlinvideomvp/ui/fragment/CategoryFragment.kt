package com.hot.kotlinvideomvp.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.CategoryContract
import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean
import com.hot.kotlinvideomvp.mvp.presenter.CategoryPresenter
import com.hot.kotlinvideomvp.ui.adapter.CategoryAdapter
import com.hot.kotlinvideomvp.ui.adapter.CategoryDetailAdapter
import com.hot.kotlinvideomvp.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {
    private var mTitle: String? = null


    private var dataList = ArrayList<CategoryBean>()

    private val adapter by lazy {
        activity?.let {
            CategoryAdapter(
                it,
                dataList,
                R.layout.item_category
            )
        }
    }


    private val mPresenter: CategoryPresenter by lazy { CategoryPresenter() }

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.adapter = adapter
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val childPosition = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!
                outRect.set(
                    if (childPosition % 2 == 0) 0 else offset,
                    offset,
                    if (childPosition % 2 == 0) offset else 0,
                    offset
                )
            }
        })
        mPresenter.getCategoryData()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_category
    }

    override fun lazyLoad() {

    }

    override fun showCategoryData(data: ArrayList<CategoryBean>) {
        adapter?.setNewData(data)
    }

    override fun showError(errorMsg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}