package io.android.ptt.domain.base.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}