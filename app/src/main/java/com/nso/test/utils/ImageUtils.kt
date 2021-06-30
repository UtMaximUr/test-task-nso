package com.nso.test.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nso.test.R


fun ImageView.setImagePath(path: String) {
    Glide.with(this).load(path).placeholder(R.drawable.ic_search_black_24dp).into(this)
}