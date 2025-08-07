package com.example.recipefinder.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.theme.AppTextStyles

enum class ListType {
    UNORDERED,
    ORDERED
}


@Composable
fun AnnotatedList(
    items: List<String>,
    listType: ListType,
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

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = when (listType) {
                        ListType.UNORDERED -> bullet
                        ListType.ORDERED -> "${index + 1}."
                    },
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

@Preview(showBackground = true)
@Composable
private fun AnnotatedListPreview(modifier: Modifier = Modifier) {
    AnnotatedList(
        items = listOf("Item 1", "Item 2", "Item 3"),
        listType = ListType.ORDERED,
        modifier = modifier,
        title = "Title"
    )
}