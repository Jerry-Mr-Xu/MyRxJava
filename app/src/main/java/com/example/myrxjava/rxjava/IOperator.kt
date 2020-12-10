package com.example.myrxjava.rxjava

interface IOperator<U, D> : IObservable<D>, IObserver<U> {
}