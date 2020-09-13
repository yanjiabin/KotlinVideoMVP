package com.hot.kotlinvideomvp.views.recycleview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hot.kotlinvideomvp.views.recycleview.MultipleType
import com.hot.kotlinvideomvp.views.recycleview.ViewHolder

abstract class CommonAdapter<T>(
    var mContext: Context, var mData: ArrayList<T>,
    private var mLayoutId: Int
) : RecyclerView.Adapter<ViewHolder>() {

    protected var mInflater:LayoutInflater?=null
    private var mTypeSupport:MultipleType<T>?=null

    private var mItemClickListener: OnItemClickListener? = null

    private var mItemLongClickListener: OnItemLongClickListener? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    constructor(conte:Context,data:ArrayList<T>,type: MultipleType<T>):this(conte
    ,data,-1){
        this.mTypeSupport= mTypeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null){
            mLayoutId = viewType
        }
        //创建view
        val view =  mInflater?.inflate(mLayoutId,parent,false)
        return ViewHolder(view!!)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder,mData[position],position)
        mItemClickListener?.let {
            holder.itemView.setOnClickListener{
                mItemClickListener!!.onItemClick(mData[position],position)
            }
        }
    }

    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener
    }
}