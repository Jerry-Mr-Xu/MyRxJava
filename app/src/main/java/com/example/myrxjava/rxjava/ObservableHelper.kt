package com.example.myrxjava.rxjava

/**
 * 被观察者帮助类
 *
 * @author xujierui
 */
class ObservableHelper<T>(
    private val observable: IObservable<T>
) {
    companion object {
        /**
         * 根据提供的被观察者来创建帮助类
         *
         * @param observable 被观察者
         */
        fun <T> create(observable: (observer: IObserver<T>) -> Unit): ObservableHelper<T> {
            return ObservableHelper(object : IObservable<T> {
                override fun subscribe(observer: IObserver<T>) {
                    observable(observer)
                }
            })
        }
    }

    /**
     * 订阅
     *
     * @param observer 观察者
     */
    fun subscribe(observer: IObserver<T>) {
        observer.onSubscribe()
        observable.subscribe(observer)
    }

    /**
     * map操作
     *
     * @param mapFun 具体map的实现
     */
    fun <R> map(mapFun: (T) -> R): ObservableHelper<R> {
        // 将被观察者经过修饰之后构建出新的帮助类
        return ObservableHelper(MapOperator(observable, mapFun))
    }

    /**
     * 切换被观察者线程
     */
    fun subscribeOn(@Schedulers.ThreadName threadName: Int): ObservableHelper<T> {
        return ObservableHelper(
            ThreadChangeOperator(
                observable,
                ThreadChangeOperator.ChangeTarget.TARGET_OBSERVABLE,
                threadName
            )
        )
    }

    /**
     * 切换观察者线程
     */
    fun observerOn(@Schedulers.ThreadName threadName: Int): ObservableHelper<T> {
        return ObservableHelper(
            ThreadChangeOperator(
                observable,
                ThreadChangeOperator.ChangeTarget.TARGET_OBSERVER,
                threadName
            )
        )
    }
}