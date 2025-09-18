package com.app.dealspot

import kotlinx.coroutines.*
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

object CoroutinesHelper {
    private val mainDispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            NSRunLoop.mainRunLoop.performBlock {
                block.run()
            }
        }
    }
    
    fun getMainDispatcher(): CoroutineDispatcher = mainDispatcher
}
