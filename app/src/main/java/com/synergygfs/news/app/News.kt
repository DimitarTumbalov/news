package com.synergygfs.news.app

import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class News : Application() {
    private var client: OkHttpClient? = null
    private val pool = Executors.newCachedThreadPool()

    override fun onCreate() {
        super.onCreate()

        instance = this

        client = OkHttpClient.Builder()
            .cache(Cache(File(cacheDir, "web-cache"), 10L * 1024 * 1024) /* 10MB */)
            .build()
    }

    companion object {
        var instance: News? = null

        fun getClient(): OkHttpClient? {
            return instance?.client
        }

        fun getPool(): ExecutorService? {
            return instance?.pool
        }
    }
}