package com.example.recipefinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.theme.CornerShapes
import com.example.recipefinder.ui.utils.formatDuration

@Composable
fun RecipeCard(
    title: String,
    duration: Int,
    imageUrl: String,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CornerShapes.Medium,
    cardColors: CardColors = CardDefaults.cardColors(
        containerColor = AppColors.Primary,
        contentColor = AppColors.OnPrimary,
    ),
    elevation: Dp = 8.dp,
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min),
        onClick = onClick,
        shape = shape,
        colors = cardColors,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImageWrapper(
                modifier = Modifier.fillMaxHeight(),
                imageUrl = imageUrl,
                placeholder = painterResource(R.drawable.img_placeholder)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = AppTextStyles.semibold,
                    color = AppColors.TextPrimary
                )

                Text(
                    text = formatDuration(duration),
                    style = AppTextStyles.regular,
                    fontSize = 14.sp,
                    color = AppColors.TextPrimary
                )
            }

                FavoriteIconButton(
                    modifier = Modifier.padding(end = 16.dp),
                    isFavorite = isFavorite,
                    onClick = onFavoriteClick
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    var isFavoriteState by remember { mutableStateOf(false) }
    RecipeCard(
        title = "Delicious Pasta",
        duration = 30,
        imageUrl = "",
        isFavorite = isFavoriteState,
        onClick = {},
        onFavoriteClick = { isFavoriteState = !isFavoriteState }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeCardFavoritePreview() {
    var isFavoriteState by remember { mutableStateOf(true) }
    RecipeCard(
        title = "Tasty Burger",
        duration = 20,
        imageUrl = "",
        isFavorite = isFavoriteState,
        onClick = {},
        onFavoriteClick = { isFavoriteState = !isFavoriteState }
    )
}