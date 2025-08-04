package com.example.recipefinder.ui.utils.text

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class ValidationResult
data object ValidationSuccess : ValidationResult()
class ValidationError(val errorMessage: String) : ValidationResult()

interface ValidatorGroup {
    operator fun plus(other: ValidatorGroup): ValidatorGroup
    fun validate(text: String): ValidationResult
}

abstract class Validator : ValidatorGroup {
    override fun plus(other: ValidatorGroup): ValidatorGroup {
        return when (other) {
            is Validator -> CompositeValidator(setOf(this, other))
            is CompositeValidator -> CompositeValidator(setOf(this) + other.validators)
            else -> throw AssertionError()
        }
    }
}

class CompositeValidator(internal val validators: Set<Validator>) : ValidatorGroup {
    override fun plus(other: ValidatorGroup): ValidatorGroup {
        return when (other) {
            is Validator -> CompositeValidator(this.validators + setOf(other))
            is CompositeValidator -> CompositeValidator(this.validators + other.validators)
            else -> throw AssertionError()
        }
    }

    override fun validate(text: String): ValidationResult {
        return validators.map { it.validate(text) }.firstOrNull { it is ValidationError }
            ?: ValidationSuccess
    }
}

class EmptyFieldValidator(private val message: String) : Validator() {
    override fun validate(text: String): ValidationResult {
        return if (text.isBlank()) {
            ValidationError(message)
        } else {
            ValidationSuccess
        }
    }
}

class MinLengthValidator(private val message: String, private val minLength: Int) : Validator() {
    override fun validate(text: String): ValidationResult {
        return if (text.length < minLength) {
            ValidationError(message)
        } else {
            ValidationSuccess
        }
    }
}

@Composable
fun emptyFieldValidator(@StringRes stringId: Int): ValidatorGroup =
    EmptyFieldValidator(stringResource(id = stringId))

@Composable
fun minLengthValidator(@StringRes stringId: Int, minLength: Int): ValidatorGroup =
    MinLengthValidator(stringResource(id = stringId), minLength)

