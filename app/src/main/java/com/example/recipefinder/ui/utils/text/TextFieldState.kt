package com.example.recipefinder.ui.utils.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.recipefinder.utils.withoutEmoji

interface InputState {
    val valid: Boolean
}

interface FieldState : InputState {
    var value: String
    val status: ValidationResult?

    fun onValueChange(value: String)
}

class TextFieldState(
    initialValue: String,
    private val valuePreprocessor: FieldStateValuePreprocessor?,
    var validator: ValidatorGroup?,
    var lazyValidate: Boolean
) : FieldState {

    companion object {
        fun Saver(
            valuePreprocessor: FieldStateValuePreprocessor?,
            validator: ValidatorGroup?,
            lazyValidate: Boolean
        ): Saver<TextFieldState, *> = Saver(
            save = { it.value },
            restore = {
                TextFieldState(it, valuePreprocessor, validator, lazyValidate)
            }
        )
    }

    override var value: String by mutableStateOf(initialValue)

    override var status: ValidationResult? by mutableStateOf(null)

    override val valid: Boolean
        get() {
            if (validator == null) return true

            status = validator?.validate(value)
            return status is ValidationSuccess
        }

    override fun onValueChange(value: String) {
        val preprocessedValue = valuePreprocessor?.invoke(this.value, value) ?: value
        this.value = preprocessedValue

        if (lazyValidate) {
            status = null
        } else {
            validator?.let {
                status = it.validate(preprocessedValue)
            }
        }
    }
}

typealias FieldStateValuePreprocessor = (oldValue: String, newValue: String) -> String

@Composable
fun rememberFieldState(
    value: String = "",
    valuePreprocessor: FieldStateValuePreprocessor? = null,
    validator: ValidatorGroup? = null,
    lazyValidate: Boolean = false
) = rememberSaveable(
    value,
    saver = TextFieldState.Saver(
        valuePreprocessor,
        validator,
        lazyValidate
    )
) {
    TextFieldState(
        initialValue = value,
        valuePreprocessor = valuePreprocessor,
        validator = validator,
        lazyValidate = lazyValidate
    )
}

val defaultFieldStateValuePreprocessor = object : FieldStateValuePreprocessor {
    override fun invoke(oldValue: String, newValue: String): String {
        return newValue
    }
}

val genericFieldStateValuePreprocessor = object : FieldStateValuePreprocessor {
    override fun invoke(oldValue: String, newValue: String): String {
        return newValue.withoutEmoji()
    }
}

fun isFormValid(vararg varArgFields: InputState): Boolean {
    var formValid = true
    varArgFields.forEach {
        if (!it.valid) {
            formValid = false
        }
    }

    return formValid
}