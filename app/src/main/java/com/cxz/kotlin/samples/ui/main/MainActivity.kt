package com.cxz.kotlin.samples.ui.main

import android.text.TextUtils
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
        btn_login.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            if (TextUtils.isEmpty(username)) {
                showDefaultMsg("账号不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showDefaultMsg("密码不能为空")
                return@setOnClickListener
            }
            mViewModel.login(username, password)
        }
        btn_logout.setOnClickListener {
            mViewModel.logout()
        }
        btn_get_banner.setOnClickListener {
            mViewModel.getBannerList()
        }
    }

    override fun startObserver() {
        mViewModel.apply {
            mLoginData.observe(this@MainActivity, Observer {
                showDefaultMsg("登录成功")
            })
            mLogoutData.observe(this@MainActivity, Observer {
                showDefaultMsg("已退出登录")
            })
            mBannerList.observe(this@MainActivity, Observer { bannerList ->
                if (bannerList.isNotEmpty()) {
                    tv_result.text = bannerList[0].title
                    Glide.with(this@MainActivity).load(bannerList[0].imagePath).into(imageView)
                }
            })
            errorMsg.observe(this@MainActivity, Observer {
                showDefaultMsg(it)
                loge("errorMsg---->>$it")
            })
        }
    }

}
