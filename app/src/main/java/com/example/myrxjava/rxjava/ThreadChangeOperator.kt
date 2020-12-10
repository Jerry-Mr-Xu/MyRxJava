package com.example.myrxjava.rxjava

import androidx.annotation.IntDef

class ThreadChangeOperator<M>(
    private var observable: IObservable<M>,
    @ChangeTarget private val target: Int,
    @Schedulers.ThreadName private val threadName: Int
) : IOperator<M, M> {
    private var observer: IObserver<M>? = null

    override fun subscribe(observer: IObserver<M>) {
        this.observer = observer
        if (target == ChangeTarget.TARGET_OBSERVABLE) {
            Schedulers.INSTANCE.doFunOnThread({
                observable.subscribe(this)
            }, threadName)
        } else {
            observable.subscribe(this)
        }
    }

    override fun onSubscribe() {
        if (target == ChangeTarget.TARGET_OBSERVER) {
            Schedulers.INSTANCE.doFunOnThread({
                observer?.onSubscribe()
            }, threadName)
        } else {
            observer?.onSubscribe()
        }
    }

    override fun onNext(msg: M) {
        if (target == ChangeTarget.TARGET_OBSERVER) {
            Schedulers.INSTANCE.doFunOnThread({
                observer?.onNext(msg)
            }, threadName)
        } else {
            observer?.onNext(msg)
        }
    }

    override fun onError(e: Throwable) {
        if (target == ChangeTarget.TARGET_OBSERVER) {
            Schedulers.INSTANCE.doFunOnThread({
                observer?.onError(e)
            }, threadName)
        } else {
            observer?.onError(e)
        }
    }

    override fun onComplete() {
        if (target == ChangeTarget.TARGET_OBSERVER) {
            Schedulers.INSTANCE.doFunOnThread({
                observer?.onComplete()
            }, threadName)
        } else {
            observer?.onComplete()
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(ChangeTarget.TARGET_OBSERVER, ChangeTarget.TARGET_OBSERVABLE)
    annotation class ChangeTarget {
        companion object {
            const val TARGET_OBSERVER = 0
            const val TARGET_OBSERVABLE = 1
        }
    }
}