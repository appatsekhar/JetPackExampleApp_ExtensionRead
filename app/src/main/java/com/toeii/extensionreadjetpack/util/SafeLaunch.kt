package com.toeii.extensionreadjetpack.util

import com.toeii.extensionreadjetpack.ERApplication
import kotlinx.coroutines.*
import org.jetbrains.anko.error
import kotlin.coroutines.CoroutineContext

/**
 * @description 解决协程处理网络请求不能处理异常
 */
private val loggingExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        ERApplication.logger.error(throwable.message)
    }

private val handlerContext: CoroutineContext = loggingExceptionHandler + GlobalScope.coroutineContext

fun CoroutineScope.safeLaunch(block: suspend () -> Unit): Job = launch(handlerContext) { block() }