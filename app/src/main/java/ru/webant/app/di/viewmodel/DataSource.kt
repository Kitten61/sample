package ru.webant.app.di.viewmodel

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

interface DataSource<T> {
    fun observe(): Observable<T>
}

interface MutableDataSource<T> : DataSource<T> {
    fun post(value: T)
}

open class PublishDataSource<T> : MutableDataSource<T> {

    private val publishRelay = PublishRelay.create<T>()

    override fun observe(): Observable<T> {
        return publishRelay.hide().observeOn(AndroidSchedulers.mainThread())
    }

    override fun post(value: T) {
        publishRelay.accept(value!!)
    }
}
