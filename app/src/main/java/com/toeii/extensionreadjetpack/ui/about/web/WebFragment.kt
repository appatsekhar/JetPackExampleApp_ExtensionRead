package com.toeii.extensionreadjetpack.ui.about.web

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ZoomButtonsController
import androidx.room.util.StringUtil
import com.qmuiteam.qmui.util.QMUILangHelper
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.databinding.FragmentWebviewBinding
import com.toeii.extensionreadjetpack.ui.widgets.ERWebView
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.UnsupportedEncodingException
import java.lang.reflect.Field
import java.net.URLDecoder

open class WebFragment : BaseFragment<FragmentWebviewBinding>(){

    private val PROGRESS_PROCESS = 0
    private val PROGRESS_GONE = 1

    private var mUrl: String = ""
    private var mTitle: String = ""
    private lateinit var mProgressHandler: ProgressHandler
    private var mIsPageFinished = false
    private var mNeedDecodeUrl = false


    private lateinit var mWebView : ERWebView

    override fun getLayoutId(): Int {
        return R.layout.fragment_webview
    }

    private fun updateTitle(title: String?) {
        if (title != null && title != "") {
            mTitle = title
            mBinding.topbar.setTitle(mTitle)
        }
    }

    private fun needDispatchSafeAreaInset(): Boolean {
        return false
    }

    override fun initView(view: View) {
        mWebView = ERWebView(activity)
        mBinding.webviewContainer.addWebView(mWebView,needDispatchSafeAreaInset())

        mProgressHandler = ProgressHandler()
    }

    override fun initData() {
        val bundle = arguments
        if (bundle != null) {
            val url = bundle.getString(ERAppConfig.WEB_EXTRA_URL)
            mTitle = bundle.getString(ERAppConfig.WEB_EXTRA_TITLE).toString()
            mNeedDecodeUrl = bundle.getBoolean(ERAppConfig.WEB_EXTRA_NEED_DECODE, false)
            if (url != null && url.isNotEmpty()) {
                handleUrl(url)
            }
        }

        mWebView.webChromeClient = ExplorerWebViewChromeClient(this)
        mWebView.webViewClient = ExplorerWebViewClient(needDispatchSafeAreaInset())
        mWebView.requestFocus(View.FOCUS_DOWN)
        setZoomControlGone(mWebView)
        mWebView.loadUrl(mUrl)
    }

    override fun initListener() {
        mBinding.topbar.addLeftBackImageButton().onClick {
            popBackStack()
        }
    }

    private fun sendProgressMessage(progressType: Int, newProgress: Int, duration: Int) {
        val msg = Message()
        msg.what = progressType
        msg.arg1 = newProgress
        msg.arg2 = duration
        mProgressHandler.sendMessage(msg)
    }

    private fun handleUrl(url: String) {
        mUrl = if (mNeedDecodeUrl) {
            val decodeURL = try {
                URLDecoder.decode(url, "utf-8")
            } catch (ignored: UnsupportedEncodingException) {
                url
            }
            decodeURL
        } else {
            url
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webviewContainer.destroy()
    }

    private fun setZoomControlGone(webView: WebView) {
        webView.settings.displayZoomControls = false
        val classType: Class<*>
        val field: Field
        try {
            classType = WebView::class.java
            field = classType.getDeclaredField("mZoomButtonsController")
            field.isAccessible = true
            val zoomButtonsController = ZoomButtonsController(
                webView
            )
            zoomButtonsController.zoomControls.visibility = View.GONE
            try {
                field.set(webView, zoomButtonsController)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }

    class ExplorerWebViewChromeClient(private val mFragment: WebFragment) : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress > mFragment.mProgressHandler.mDstProgressIndex) {
                mFragment.sendProgressMessage(mFragment.PROGRESS_PROCESS, newProgress, 100)
            }
        }

        override fun onShowCustomView(view: View, callback: CustomViewCallback) {
            callback.onCustomViewHidden()
        }

        override fun onHideCustomView() {

        }
    }


    protected inner class ExplorerWebViewClient(needDispatchSafeAreaInset: Boolean) :
        QMUIWebViewClient(needDispatchSafeAreaInset, true) {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if ("" == mTitle) {
                updateTitle(view.title)
            }else{
                updateTitle(mTitle)
            }
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PROGRESS_PROCESS, 30, 500)
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            sendProgressMessage(PROGRESS_GONE, 100, 0)
        }
    }

    @SuppressLint("HandlerLeak")
    private inner class ProgressHandler : Handler() {

        var mDstProgressIndex: Int = 0
        var mDuration: Int = 0
        var mAnimator: ObjectAnimator? = null


        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PROGRESS_PROCESS -> {
                    mIsPageFinished = false
                    mDstProgressIndex = msg.arg1
                    mDuration = msg.arg2
                    mBinding.progressBar.visibility = View.VISIBLE
                    if (mAnimator != null && mAnimator!!.isRunning) {
                        mAnimator!!.cancel()
                    }
                    mAnimator = ObjectAnimator.ofInt(mBinding.progressBar, "progress", mDstProgressIndex)
                    mAnimator!!.duration = mDuration.toLong()
                    mAnimator!!.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            if (mBinding.progressBar.progress == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500)
                            }
                        }
                    })
                    mAnimator!!.start()
                }
                PROGRESS_GONE -> {
                    mDstProgressIndex = 0
                    mDuration = 0
                    mBinding.progressBar.progress = 0
                    mBinding.progressBar.visibility = View.GONE
                    if (mAnimator != null && mAnimator!!.isRunning) {
                        mAnimator!!.cancel()
                    }
                    mAnimator = ObjectAnimator.ofInt(mBinding.progressBar, "progress", 0)
                    mAnimator!!.duration = 0
                    mAnimator!!.removeAllListeners()
                    mIsPageFinished = true
                }
                else -> {
                }
            }
        }
    }


}