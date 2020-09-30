package com.hot.kotlinvideomvp.net

import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.api.ApiService
import com.hot.kotlinvideomvp.api.UrlConstant
import com.hot.kotlinvideomvp.utils.AppUtils
import com.hot.kotlinvideomvp.utils.Preference
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by anderson
 * on 2020/9/3.
 * desc:
 */
object RetrofitManager {
    private var token:String by Preference("token","")

    val service:ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED) {
         getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个打印器,打印所有的log信息
        val httpLoggingIntercepter = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic, headers
        httpLoggingIntercepter.level =HttpLoggingInterceptor.Level.HEADERS

        //
        val cacheFile = File(MyApplication.context.cacheDir,"cache")
        val cache = Cache(cacheFile,1024*1024*50)
        return OkHttpClient().newBuilder()
            .addInterceptor(addQueryParameterInterceptor())
            .addInterceptor(httpLoggingIntercepter)
            .addInterceptor(addHeaderInterceptor())
            .cache(cache)
            .connectTimeout(60L,TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    /**
     *  设置拦截头部
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("token", token)
                .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }

    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

}