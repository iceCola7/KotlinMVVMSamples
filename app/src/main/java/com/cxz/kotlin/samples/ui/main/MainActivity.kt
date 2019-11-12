package com.cxz.kotlin.samples.ui.main

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cxz.kotlin.mvvm.base.BaseVMActivity
import com.cxz.kotlin.samples.R
import com.cxz.kotlin.samples.ext.loge
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.base_toolbar.*

class MainActivity : BaseVMActivity<MainViewModel>() {

    override fun attachVMClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initView() {
        base_toolbar.title = ""
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        base_title_tv.text = "Main"
    }

    override fun initData() {
        btn_get_banner.setOnClickListener {
            mViewModel.getBannerList()
        }
    }

    override fun startObserver() {
        mViewModel.apply {
            mBannerList.observe(this@MainActivity, Observer { bannerList ->
                if (bannerList.isNotEmpty()) {
                    tv_result.text = bannerList[0].title
                    Glide.with(this@MainActivity).load(bannerList[0].imagePath).into(imageView)
                }
            })
            errorMsg.observe(this@MainActivity, Observer {
                loge("errorMsg---->>$it")
            })
        }
    }

}
