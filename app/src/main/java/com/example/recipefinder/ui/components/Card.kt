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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.recipes.RecipeItemViewModel
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.theme.CornerShapes
import com.example.recipefinder.ui.utils.doubleShadowDrop
import com.example.recipefinder.ui.utils.formatDuration

@Composable
fun RecipeCard(
    recipe: RecipeItemViewModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CornerShapes.Medium,
    cardColors: CardColors = CardDefaults.cardColors(
        containerColor = AppColors.Primary,
        contentColor = AppColors.OnPrimary,
    ),
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .doubleShadowDrop(shape),
        onClick = onClick,
        shape = shape,
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImageWrapper(
                modifier = Modifier.fillMaxHeight(),
                imageUrl = recipe.imageUrl,
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
                    text = recipe.title,
                    style = AppTextStyles.semibold,
                    color = AppColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatDuration(recipe.duration),
                    style = AppTextStyles.regular,
                    fontSize = 14.sp,
                    color = AppColors.TextPrimary
                )
            }

            FavoriteIconButton(
                modifier = Modifier.padding(end = 16.dp),
                isFavorite = recipe.isFavorite,
                onClick = onFavoriteClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    RecipeCard(
        recipe = RecipeItemViewModel(
            id = "1",
            title = "Refreshing Black Bean Salsa Salad with Chickpeas",
            duration = 20,
            isFavorite = false,
            imageUrl = "",
            ingredients = listOf(),
            instructions = listOf()
        ),
        onClick = {},
        onFavoriteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeCardFavoritePreview() {
    RecipeCard(
        recipe = RecipeItemViewModel(
            id = "2",
            title = "Delicious Pasta",
            duration = 20,
            isFavorite = true,
            imageUrl = "",
            ingredients = listOf(),
            instructions = listOf()
        ),
        onClick = {},
        onFavoriteClick = {}
    )
}