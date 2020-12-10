package com.example.myrxjava.rxjava

/**
 * Map操作符
 * 既是观察者又是被观察者
 *
 * @author xujierui
 */
class MapOperator<U, D>(
    /**
     * 原始被观察者
     */
    private val observable: IObservable<U>,
    /**
     * map操作
     */
    private val operator: (U) -> D
) : IOperator<U, D> {
    /**
     * 原始观察者
     */
    private var observer: IObserver<D>? = null

    override fun subscribe(observer: IObserver<D>) {
        // 将外部提供的观察者经过修饰之后订阅原始被观察者
        this.observer = observer
        observable.subscribe(this)
    }

    override fun onSubscribe() {
        observer?.onSubscribe()
    }

    override fun onNext(msg: U) {
        // 将消息经过变换后交给观察者
        observer?.onNext(operator.invoke(msg))
    }

    override fun onError(e: Throwable) {
        observer?.onError(e)
    }

    override fun onComplete() {
        observer?.onComplete()
    }
}