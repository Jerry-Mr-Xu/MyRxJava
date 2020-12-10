package com.example.myrxjava.rxjava

/**
 * 观察者
 *
 * @author xujierui
 */
interface IObserver<T> {
    /**
     * 当订阅一个被观察者时的回调
     */
    fun onSubscribe()

    /**
     * 被观察者发送的事件
     *
     * @param msg 发送的事件
     */
    fun onNext(msg: T)

    /**
     * 被观察者发送了错误信息
     *
     * @param e 错误
     */
    fun onError(e: Throwable)

    /**
     * 被观察者发送了完成信息
     */
    fun onComplete()
}