package com.example.golpezero

import kotlinx.coroutines.*
import kotlin.reflect.full.findAnnotation


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeAnnotation(val dispatcher: DispatcherType)


enum class DispatcherType {
    IO, Main, Default
}


fun getDispatcher(type: DispatcherType): CoroutineDispatcher {
    return when (type) {
        DispatcherType.IO -> Dispatchers.IO
        DispatcherType.Main -> Dispatchers.Main
        DispatcherType.Default -> Dispatchers.Default
    }
}


@CoroutineScopeAnnotation(DispatcherType.IO)
fun carregarDados() {
    println("Carregando dados do banco...")
}


fun executarComCoroutine(target: () -> Unit) {
    val method = target::class.members.firstOrNull { it.name == "invoke" }
    val annotation = method?.findAnnotation<CoroutineScopeAnnotation>()

    val dispatcher = if (annotation != null) getDispatcher(annotation.dispatcher) else Dispatchers.Default

    CoroutineScope(dispatcher).launch {
        target()
    }
}


fun main() {
    executarComCoroutine(::carregarDados)
    Thread.sleep(1000) // Espera coroutine rodar antes de encerrar o programa
}
