package com.hot.kotlinvideomvp.views.recycleview

import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private var mView: SparseArray<View>? = null

    init {
        mView = SparseArray()
    }

    /**
     *  根据id找控件
     */
    fun <T : View> getView(viewId: Int): T {
        //对已经有的view 做缓存
        var view: View? = mView?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T;
    }

    /**
     *  根据id找viewgroup
     */
    fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        var view: View? = mView?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }

    @SuppressLint("SetTextI18n")
    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId);
        view.text = "" + text
        return this
    }

    fun setHintText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId);
        view.hint = "" + text
        return this
    }

    /**
     * 设置本地图片资源
     *
     */

    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val imageView = getView<ImageView>(viewId);
        imageView.setImageResource(resId)
        return this
    }

    /**
     * 加载图片资源路径
     */

    fun setImagePath(viewId: Int, imageLoad: HolderImageLoader): ViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageLoad.loadImage(imageView,imageLoad.path)
        return this
    }


    abstract class HolderImageLoader(val path : String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }

    /**
     * 设置是否需要隐藏或者显示
     */
    fun setViewVisibility(viewId: Int,visibility:Int):ViewHolder{
        val view = getView<View>(viewId)
        view.visibility = visibility
        return this
    }


    /**
     *  设置条目点击事件
     */
    fun setOnItemClickListener(listener:View.OnClickListener){
        itemView.setOnClickListener(listener)
    }

    /**
     *  设置条目长按事件
     */
    fun setOnLongItemClickListener(longClickListener:View.OnLongClickListener){
        itemView.setOnLongClickListener(longClickListener)
    }

}