package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.theme.CornerShapes
import com.example.recipefinder.ui.utils.NoRippleInteractionSource
import com.example.recipefinder.ui.utils.keyboardAsState
import com.example.recipefinder.ui.utils.text.FieldState
import com.example.recipefinder.ui.utils.text.rememberFieldState

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    fieldState: FieldState,
    onSearchIconClick: () -> Unit,
    searchFieldFocusRequester: FocusRequester,
    shape: Shape = CornerShapes.Large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val focused by interactionSource.collectIsFocusedAsState()
    val isKeyboardOpened by keyboardAsState()
    val showClearButton by remember {
        derivedStateOf {
            fieldState.value.isNotEmpty()
        }
    }

    LaunchedEffect(isKeyboardOpened) {
        if (focused && !isKeyboardOpened) {
            focusManager.clearFocus()
        }
    }

    Box(
        modifier = modifier
            .height(40.dp)
            .background(AppColors.Primary, shape = shape)
            .border(
                width = 1.dp,
                color = AppColors.Border,
                shape = shape
            )
            .clickable(interactionSource = interactionSource, indication = null, onClick = {})
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(searchFieldFocusRequester),
                value = fieldState.value,
                onValueChange = {
                    fieldState.onValueChange(it)
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = AppColors.TextPrimary
                ),
                maxLines = 1,
                singleLine = true,
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                decorationBox = {
                    if (fieldState.value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.results_search_bar_placeholder),
                            color = AppColors.TextPlaceholder,
                            style = AppTextStyles.regular
                        )
                    }
                    it()
                }
            )

            if (!showClearButton) {
                Icon(
                    modifier = Modifier
                        .size(size = 16.dp)
                        .clickable(
                            interactionSource = remember { NoRippleInteractionSource() },
                            indication = null,
                            onClick = {
                                onSearchIconClick()
                            }
                        ),
                    painter = painterResource(id = R.drawable.ic_searchbar),
                    contentDescription = null,
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(size = 16.dp)
                        .clickable(
                            interactionSource = remember { NoRippleInteractionSource() },
                            indication = null,
                            onClick = { fieldState.onValueChange("") }
                        ),
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = Devices.PIXEL_6
)
@Composable
fun SearchBarPreview() {
    val searchFieldState = rememberFieldState("")
    Box {
        SearchBar(
            fieldState = searchFieldState,
            onSearchIconClick = {},
            searchFieldFocusRequester = FocusRequester()
        )
    }
}