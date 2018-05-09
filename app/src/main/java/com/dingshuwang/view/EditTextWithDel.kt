package com.dingshuwang.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText

import com.dingshuwang.R

/**
 * @author Davis 2015-03-21
 */
class EditTextWithDel : EditText {
    var imgAble: Drawable? = null
    private var mContext: Context? = null
    private var mDeleteClickListener: OnDeleteClickListener? = null

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init()
    }

    private fun init() {
        // imgInable =
        // mContext.getResources().getDrawable(R.drawable.delete_gray);
        imgAble = mContext!!.resources.getDrawable(
                R.mipmap.delete_icon_edit)
        addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                setDrawable()
            }
        })
        setDrawable()
    }

    // 设置删除图片
    private fun setDrawable() {
        if (length() > 0)
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null)
        else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    fun setHidenDrawable() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    // 处理删除事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (imgAble != null && event.action == MotionEvent.ACTION_DOWN) {
            val eventX = event.rawX.toInt()
            val eventY = event.rawY.toInt()
            Log.e(TAG, "eventX = $eventX; eventY = $eventY")
            val rect = Rect()
            getGlobalVisibleRect(rect)
            rect.left = rect.right - 80
            if (rect.contains(eventX, eventY)) {
                setText("")
                if (null != mDeleteClickListener) {
                    mDeleteClickListener!!.onDeleteClick()
                }
            }

        }
        return super.onTouchEvent(event)
    }

    fun setOnDeleteClickListener(
            deleteClickListener: OnDeleteClickListener) {
        this.mDeleteClickListener = deleteClickListener
    }

    interface OnDeleteClickListener {
        fun onDeleteClick()
    }

    companion object {
        private val TAG = "Davis"
    }
}