package com.example.myrxjava.rxjava

/**
 * 被观察者
 *
 * @author xujierui
 */
interface IObservable<T> {
    fun subscribe(observer: IObserver<T>)
}