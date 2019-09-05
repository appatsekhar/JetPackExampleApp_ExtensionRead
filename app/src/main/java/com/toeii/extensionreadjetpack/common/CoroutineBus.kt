package com.toeii.extensionreadjetpack.common

import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.lang.Exception

val UI: CoroutineDispatcher = Dispatchers.Main

object CoroutineBus: CoroutineScope {

    private val TAG = "CoroutineBus"

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.Default + job

    private val contextMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
    private val mStickyEventMap = ConcurrentHashMap<Class<*>, Any>()

    @JvmStatic
    fun <T> register(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val eventDataMap = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback)
    }

    @JvmStatic
    fun <T> register(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit,
        eventFail:(Throwable)->Unit
    ) {
        val eventDataMap = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback, eventFail)
    }

    @JvmStatic
    fun <T> registerSticky(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val eventDataMap = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback)

        val event = mStickyEventMap[eventClass]
        event?.let {

            postEvent(it)
        }
    }

    @JvmStatic
    fun <T> registerSticky(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit,
        eventFail:(Throwable)->Unit
    ) {
        val eventDataMap = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback, eventFail)

        val event = mStickyEventMap[eventClass]
        event?.let {

            postEvent(it)
        }
    }

    @JvmStatic
    fun post(event: Any, delayTime: Long = 0) {

        if (delayTime > 0) {
            launch {
                delay(delayTime)
                postEvent(event)
            }
        } else {
            postEvent(event)
        }
    }

    @JvmStatic
    fun postSticky(event: Any) {

        mStickyEventMap[event.javaClass] = event
    }

    @JvmStatic
    fun unregisterAllEvents() {

        Log.i(TAG,"unregisterAllEvents()")

        coroutineContext.cancelChildren()
        for ((_, eventDataMap) in contextMap) {
            eventDataMap.values.forEach {
                it.cancel()
            }
            eventDataMap.clear()
        }
        contextMap.clear()
    }

    @JvmStatic
    fun unregister(contextName: String) {

        Log.i(TAG,"$contextName")

        val cloneContexMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContexMap.putAll(contextMap)
        val map = cloneContexMap.filter { it.key == contextName }
        for ((_, eventDataMap) in map) {
            eventDataMap.values.forEach {
                it.cancel()
            }
            eventDataMap.clear()
        }
        contextMap.remove(contextName)
    }

    @JvmStatic
    fun <T> removeStickyEvent(eventType: Class<T>) {
        mStickyEventMap.remove(eventType)
    }

    private fun postEvent(event: Any) {

        val cloneContexMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContexMap.putAll(contextMap)
        for ((_, eventDataMap) in cloneContexMap) {
            eventDataMap.keys
                .firstOrNull { it == event.javaClass || it == event.javaClass.superclass }
                ?.let { key -> eventDataMap[key]?.postEvent(event) }
        }
    }
}



data class EventData<T>(
    val coroutineScope: CoroutineScope,
    val eventDispatcher: CoroutineDispatcher,
    val onEvent: (T) -> Unit,
    val exception: ((Throwable)->Unit)? = null
) {

    private val channel = Channel<T>()

    init {
        coroutineScope.launch {
            channel.consumeEach {
                launch(eventDispatcher) {

                    if (exception!=null) {

                        try{
                            onEvent(it)
                        } catch (e:Exception) {

                            exception.invoke(e)
                        }
                    } else {

                        onEvent(it)
                    }
                }
            }
        }
    }

    fun postEvent(event: Any) {
        if (!channel.isClosedForSend) {

            coroutineScope.launch {
                channel.send(event as T)
            }
        } else {
            println("Channel is closed for send")
        }
    }

    fun cancel() {
        channel.cancel()
    }
}