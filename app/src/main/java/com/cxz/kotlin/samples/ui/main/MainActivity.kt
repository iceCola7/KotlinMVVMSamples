package com.cxz.kotlin.samples.ui.main

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.cxz.kotlin.mvvm.base.BaseVMActivity
import com.cxz.kotlin.mvvm.ext.setSingleClickListener
import com.cxz.kotlin.samples.R
import com.cxz.kotlin.samples.utils.DialogUtil
import com.cxz.kotlin.samples.utils.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.base_toolbar.*

class MainActivity : BaseVMActivity<MainViewModel>() {

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, "正在加载")
    }

    override fun attachVMClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun initView() {
        base_toolbar.title = ""
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        base_title_tv.text = "Main"
    }

    override fun initData() {
        btn_login.setSingleClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            if (TextUtils.isEmpty(username)) {
                showDefaultMsg("账号不能为空")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showDefaultMsg("密码不能为空")
                return@setSingleClickListener
            }
            mViewModel.login(username, password)
        }
        btn_logout.setSingleClickListener {
            mViewModel.logout()
        }
        btn_get_banner.setSingleClickListener {
            mViewModel.getBannerList()
        }
        btn_collect.setSingleClickListener {
            mViewModel.getCollectList(0)
        }
        btn_permission.setSingleClickListener {
            PermissionHelper.requestCameraPermission(this) {
                showDefaultMsg("相机权限申请成功")
            }
        }
    }

    override fun startObserver() {
        super.startObserver()
        mViewModel.mLoginData.observe(this) {
            showDefaultMsg("登录成功")
        }
        mViewModel.mLogoutData.observe(this) {
            showDefaultMsg("已退出登录")
        }
        mViewModel.mBannerList.observe(this) { bannerList ->
            if (bannerList.isNotEmpty()) {
                tv_result.text = bannerList[0].title
                Glide.with(this@MainActivity).load(bannerList[0].imagePath).into(imageView)
            }
        }
        mViewModel.mCollectResponseBody.observe(this) { body ->
            if (body.datas.isNotEmpty()) {
                tv_result.text = body.datas[0].title
            }
        }
    }

}
