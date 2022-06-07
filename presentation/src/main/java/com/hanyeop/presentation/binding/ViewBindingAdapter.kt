package com.hanyeop.presentation.binding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hanyeop.domain.utils.Result
import com.hanyeop.presentation.R

object ViewBindingAdapter {

    // 음악 검색 이미지 바인딩
    @BindingAdapter("searchImage")
    @JvmStatic
    fun ImageView.setSearchImage (imageUrl: Any){
        Glide.with(this.context)
            .load(imageUrl)
            .override(80,80)
            .placeholder(R.drawable.image_loading)
            .into(this)
        this.clipToOutline = true
    }

    // 음악 상세 이미지 바인딩
    @BindingAdapter("detailImage")
    @JvmStatic
    fun ImageView.setDetailImage (imageUrl: Any){
        Glide.with(this.context)
            .load(imageUrl)
            .override(200,200)
            .placeholder(R.drawable.image_loading)
            .into(this)
        this.clipToOutline = true
    }

    // 로딩 상태 표시
    @JvmStatic
    @BindingAdapter("isLoading")
    fun View.bindIsLoading(result: Result<*>) {
        this.isVisible = result is Result.Loading
    }
}