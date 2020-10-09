package com.hot.kotlinvideomvp.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.utils.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by anderson
 * on 2020/10/9.
 * desc:
 */
class SplashActivity : BaseActivity() {

    private var textTypeface: Typeface? = null

    private var descTypeface: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface =
            Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeface = Typeface.createFromAsset(
            MyApplication.context.assets,
            "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
        )
    }


    override fun start() {

    }

    override fun initView() {


        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeface
        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"

        //渐变启动欢迎界面
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                gotoMain()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        checkPermission();
    }

    private fun checkPermission() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        EasyPermissions.requestPermissions(this, "应用需要以下权限，请允许", 1, *permissions)
    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun initData() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 1) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE) && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    alphaAnimation?.let {
                        iv_web_icon.startAnimation(it)
                    }
                }
            }
        }
    }
}