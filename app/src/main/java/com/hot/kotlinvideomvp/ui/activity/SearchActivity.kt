package com.hot.kotlinvideomvp.ui.activity

import android.graphics.Typeface
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.contract.SearchContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.SearchPresenter
import com.hot.kotlinvideomvp.showToast
import com.hot.kotlinvideomvp.ui.adapter.CategoryDetailAdapter
import com.hot.kotlinvideomvp.ui.adapter.HotKeywordsAdapter
import com.hot.kotlinvideomvp.utils.StatusBarUtil
import com.hot.kotlinvideomvp.utils.ViewAnimUtils
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by anderson
 * on 2020/9/21.
 * desc:
 */
class SearchActivity : BaseActivity(), SearchContract.View {

    private var mTextTypeface: Typeface? = null
    private val mResultAdapter by lazy {
        CategoryDetailAdapter(
            this,
            itemList,
            R.layout.item_category_detail
        )
    }
    private var keyWords: String? = null
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var mLoadMore = false
    private var mHotKeywordsAdapter :HotKeywordsAdapter?=null
    private val mPresenter: SearchPresenter by lazy { SearchPresenter() }

    init {

        mTextTypeface = Typeface.createFromAsset(
            MyApplication.context.assets,
            "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
        )
    }

    override fun start() {
        mPresenter.requestHotWordData()
    }

    override fun initView() {
        mPresenter.attachView(this)
        tv_title_tip.typeface = mTextTypeface
        tv_hot_search_words.typeface = mTextTypeface
        //初始化查询历史搜索记录
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter

        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = recyclerView.layoutManager?.itemCount
                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!mLoadMore && lastVisibleItem == (itemCount?.minus(1))) {
                    mLoadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        //点击取消按钮
        tv_cancel.setOnClickListener {
            onBackPressed()
        }

        et_search_view.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //close keyboard todo
                    keyWords = et_search_view.text.toString().trim()
                    if (keyWords.isNullOrEmpty()) {
                        showToast("请输入你感兴趣的关键词")
                    } else {
                        mPresenter.querySearchData(keyWords!!)
                    }
                }
                return false
            }
        })
        mLayoutStatusView = multipleStatusView


        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    override fun initData() {
        setUpView()
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBord(et_search_view, applicationContext)
    }

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mHotKeywordsAdapter = HotKeywordsAdapter(this, string, R.layout.item_flow_text)

        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式

        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mHotKeywordsAdapter
        //设置 Tag 的点击事件
        mHotKeywordsAdapter?.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mPresenter.querySearchData(it)
        }
    }
    /**
     * 显示热门关键字的 流式布局
     */
    private fun showHotWordView(){
        layout_hot_words.visibility = View.VISIBLE
        layout_content_result.visibility = View.GONE
    }

    /**
     *  设置搜索结果显示
     */
    override fun setSearchResult(issue: HomeBean.Issue) {
        mLoadMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)
        itemList = issue.itemList
        mResultAdapter.addData(itemList)
    }


    /**
     * 隐藏热门关键字的 View
     */
    private fun hideHotWordView(){
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    override fun closeSoftKeyboard() {
        closeKeyBord(et_search_view, applicationContext)
    }

    override fun setEmptyView() {

    }

    override fun showError(errorMsg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    // 返回事件
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(
                this, rel_frame,
                fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {
                        defaultBackPressed()
                    }

                    override fun onRevealShow() {

                    }
                })
        } else {
            defaultBackPressed()
        }
    }

    // 默认回退
    private fun defaultBackPressed() {
        closeSoftKeyboard()
        super.onBackPressed()
    }

}