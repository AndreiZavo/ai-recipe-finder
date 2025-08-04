package com.example.recipefinder.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefinder.R
import com.example.recipefinder.ui.components.SearchBar
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.utils.text.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun RecipesScreen(
) {
    val searchQueryFlow = MutableStateFlow("")
    val searchFieldState = TextFieldState(
        initialValue = "",
        valuePreprocessor = { _, newValue ->
            searchQueryFlow.update { newValue }
            newValue
        },
        validator = null,
        lazyValidate = false
    )
    val searchFieldFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColors.Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                fieldState = searchFieldState,
                onSearchIconClick = {},
                searchFieldFocusRequester = searchFieldFocusRequester
            )

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.favorites_title),
                style = AppTextStyles.bold,
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = Devices.PIXEL_6
)
@Composable
fun RecipesScreenPreview() {
    RecipesScreen()
}