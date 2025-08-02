package com.example.recipefinder.ui.base

import android.annotation.SuppressLint
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import com.example.recipefinder.utils.DataFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposableNaming")
@Composable
fun <E : Any> BaseViewModel<E>.collectEvents(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: (suspend (event: E) -> Unit)
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val updatedOnEvent by rememberUpdatedState(newValue = onEvent)

    LaunchedEffect(eventsFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            eventsFlow.collect {
                runCatching { updatedOnEvent(it) }
            }
        }
    }
}

open class BaseViewModel<E> : ViewModel(), CoroutineScope by MainScope() {

    val eventsFlow = MutableSharedFlow<E>()

    protected suspend fun emitEvent(event: E) {
        eventsFlow.emit(event)
    }

    protected fun <T> DataFlow<T>.execute(
        withLoading: Boolean = true,
        block: suspend DataFlow.ExecuteScope<T>.() -> Unit
    ) = this.executeIn(this@BaseViewModel, withLoading, block)

    @CallSuper
    override fun onCleared() {
        this.cancel()
        super.onCleared()
    }
}
