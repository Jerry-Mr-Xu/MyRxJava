package com.example.myrxjava.rxjava

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntDef
import java.util.concurrent.Executors

class Schedulers {
    companion object {
        val INSTANCE: Schedulers by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Schedulers()
        }
    }

    private var ioThreadPool = Executors.newCachedThreadPool()
    private var uiHandler = Handler(Looper.getMainLooper())

    fun doFunOnThread(function: () -> Unit, @ThreadName threadName: Int) {
        when (threadName) {
            ThreadName.IO -> ioThreadPool.submit(function)
            ThreadName.MAIN -> uiHandler.post(function)
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(ThreadName.MAIN, ThreadName.IO)
    annotation class ThreadName {
        companion object {
            const val MAIN = 0
            const val IO = 1
        }
    }
}