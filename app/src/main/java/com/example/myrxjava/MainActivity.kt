package com.example.myrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrxjava.rxjava.ObservableHelper
import com.example.myrxjava.rxjava.IObserver
import com.example.myrxjava.rxjava.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test()
    }

    private fun test() {
        ObservableHelper.create { observer: IObserver<String> ->
            println("订阅成功")
            println("当前线程为${Thread.currentThread().name}")
            println("发送第一条消息")
            observer.onNext("第一条消息")
            println("发送完成消息")
            observer.onComplete()
        }
            .map { msg -> msg.replace("一", "1") }
            .subscribeOn(Schedulers.ThreadName.IO)
            .observerOn(Schedulers.ThreadName.MAIN)
            .subscribe(object : IObserver<String> {
                override fun onSubscribe() {
                    println("订阅成功回调")
                    println("当前线程为${Thread.currentThread().name}")
                }

                override fun onNext(msg: String) {
                    println("接收到${msg}")
                    println("当前线程为${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {
                    println("接收到错误消息")
                    println("当前线程为${Thread.currentThread().name}")
                }

                override fun onComplete() {
                    println("接收到完成消息")
                    println("当前线程为${Thread.currentThread().name}")
                }
            })

    }
}