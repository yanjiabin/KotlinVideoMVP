package com.hot.kotlinvideomvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.ui.activity.CategoryDetailActivity
import com.hot.kotlinvideomvp.utils.Constants
import com.hot.kotlinvideomvp.views.recycleview.ViewHolder
import com.hot.kotlinvideomvp.views.recycleview.adapter.CommonAdapter
import org.w3c.dom.Text

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
class CategoryAdapter(context: Context, data: ArrayList<CategoryBean>, layoutId: Int) :
    CommonAdapter<CategoryBean>(context, data, layoutId) {

    private var textTypeface: Typeface? = null

    init {
        textTypeface = Typeface.createFromAsset(
            MyApplication.context.assets,
            "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
        );
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {

        // 设置分类的名字
        holder.setText(R.id.tv_category_name, "#${data.name}")
        holder.getView<TextView>(R.id.tv_category_name).typeface = textTypeface
        holder.setImagePath(R.id.iv_category,
            object : ViewHolder.HolderImageLoader(data.headerImage) {
                override fun loadImage(iv: ImageView, path: String) {
                    Glide.with(mContext).load(path).into(iv)
                }
            })

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent= Intent(mContext as Activity,CategoryDetailActivity::class.java)
                intent.putExtra(Constants.BUNDLE_CATEGORY_DATA,data)
                mContext.startActivity(intent)
            }
        })

    }

    fun setNewData(data: ArrayList<CategoryBean>) {
        mData.clear()
        mData = data
        notifyDataSetChanged()
    }
}

