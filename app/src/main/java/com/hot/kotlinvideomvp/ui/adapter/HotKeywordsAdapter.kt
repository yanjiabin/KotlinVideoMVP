package com.hot.kotlinvideomvp.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.views.recycleview.ViewHolder
import com.hot.kotlinvideomvp.views.recycleview.adapter.CommonAdapter
import com.hot.kotlinvideomvp.views.recycleview.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.item_flow_text.view.*

/**
 * Created by anderson
 * on 2020/9/23.
 * desc:
 */
class HotKeywordsAdapter(context: Context, dataList: ArrayList<String>, layoutId: Int) :
    CommonAdapter<String>(context, dataList, layoutId) {
    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnTagItemClick: ((tag: String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {

        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_title, data)

        val layoutParams = holder.getView<TextView>(R.id.tv_title).layoutParams
        if (layoutParams is FlexboxLayoutManager.LayoutParams) {
            layoutParams.flexGrow = 1.0f
        }

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mOnTagItemClick?.invoke(data)
            }
        })
    }

}