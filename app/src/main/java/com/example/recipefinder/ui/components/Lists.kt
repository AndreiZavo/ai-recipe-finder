package com.example.recipefinder.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.theme.AppTextStyles

@Composable
fun UnorderedList(
    items: List<String>,
    modifier: Modifier = Modifier,
    title: String? = null,
    bullet: String = "\u2022", // •
) {
    Column(
        modifier = modifier
    ) {
        title?.let {
            Text(
                text = it,
                style = AppTextStyles.bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items.forEach { item ->
            Row(
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = bullet,
                    style = AppTextStyles.regular,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = item,
                    style = AppTextStyles.regular
                )
            }
        }
    }
}

@Composable
fun OrderedList(
    items: List<String>,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    Column(
        modifier = modifier
    ) {
        title?.let {
            Text(
                text = it,
                style = AppTextStyles.bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${index + 1}.",
                    style = AppTextStyles.regular,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = item,
                    style = AppTextStyles.regular
                )
            }
        }
    }
}