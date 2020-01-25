package com.hanhanlove.util

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * create by zuyuan on 2019/12/24
 *
 * 能够在onStop或者onDestroy时移除任务的Handler
 */
object LiveHandler {

    private val handler = Handler(Looper.getMainLooper())

    /*默认在onStop时移除任务，可选OnDestroy时移除*/
    fun safePost(lifecycle: Lifecycle,
                 task: Runnable,
                 rmOnDestroy: Boolean = false) {
        handler.post(task)
        if (rmOnDestroy) {
            initObserver(lifecycle, LiveObserverOnDestroy(handler, task))
        } else {
            initObserver(lifecycle, LiveObserverOnStop(handler, task))
        }
    }

    fun safePostDelay(lifecycle: Lifecycle,
                      task: Runnable, delay: Long,
                      rmOnDestroy: Boolean = false) {
        handler.postDelayed(task, delay)
        if (rmOnDestroy) {
            initObserver(lifecycle, LiveObserverOnDestroy(handler, task))
        } else {
            initObserver(lifecycle, LiveObserverOnStop(handler, task))
        }
    }

    private fun initObserver(lifecycle: Lifecycle,
                             observer: LifecycleObserver) {
        lifecycle.addObserver(observer)
    }

    class LiveObserverOnStop(private val handler: Handler, private val task: Runnable)
        : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun clearTask() {
            handler.removeCallbacks(task)
        }
    }

    class LiveObserverOnDestroy(private val handler: Handler, private val task: Runnable)
        : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun clearTask() {
            handler.removeCallbacks(task)
        }
    }
}