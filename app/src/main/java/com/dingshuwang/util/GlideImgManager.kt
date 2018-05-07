package com.dingshuwang.util

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dingshuwang.R

import java.io.File

/**
 * Created by tx on 2017/6/6.
 */

object GlideImgManager {


    fun loadImage(context: Context, url: String, erroImg: Int, emptyImg: Int, iv: ImageView) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv)
    }

    fun loadImage(context: Context, url: String, iv: ImageView) {
        //原生 APIsignature(new StringSignature(Math.random()+"")).

        Glide.with(context).load(url).crossFade().error(R.mipmap.ic_default).into(iv)
    }

    fun loadGifImage(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.ic_default).into(iv)
    }


    fun loadCircleImage(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).error(R.mipmap.ic_default).transform(GlideCircleTransform(context)).into(iv)
    }

    fun loadRoundCornerImage(context: Context, url: String, iv: ImageView) {
        Glide.with(context).load(url).error(R.mipmap.ic_default).transform(GlideRoundTransform(context, 10)).into(iv)
    }


    fun loadImage(context: Context, file: File, imageView: ImageView) {
        Glide.with(context)
                .load(file)
                .into(imageView)


    }

    fun loadImage(context: Context, resourceId: Int, imageView: ImageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView)
    }


}
