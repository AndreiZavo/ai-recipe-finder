package com.example.recipefinder.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.theme.CornerShapes

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
    enabled: Boolean = true,
    fontSize: TextUnit = 16.sp,
    textColor: Color = AppColors.TextSecondary,
    textAlign: TextAlign? = TextAlign.Center,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = AppColors.PrimaryButton,
        contentColor = AppColors.Primary,
        disabledContainerColor = AppColors.ButtonDisabled,
        disabledContentColor = AppColors.Primary
    ),
    shape: RoundedCornerShape = CornerShapes.Medium,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    borderStroke: BorderStroke? = null,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        border = borderStroke,
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            style = AppTextStyles.regular,
            fontSize = fontSize,
            color = textColor,
            textAlign = textAlign
        )
    }
}


@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconTint: Color = AppColors.IconTint,
    unselectedTint: Color = AppColors.BorderSecondary
) {
    val iconImageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    val animatedIconTint by animateColorAsState(
        targetValue = if (isFavorite) iconTint else unselectedTint,
        animationSpec = tween(durationMillis = 300)
    )

    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = iconImageVector,
            contentDescription = stringResource(
                if (isFavorite) R.string.favorite_content_description_remove
                else R.string.favorite_content_description_add
            ),
            tint = animatedIconTint,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoriteButtonPreview() {
    var isFavoriteState by remember { mutableStateOf(false) }
    FavoriteIconButton(
        isFavorite = isFavoriteState,
        onClick = { isFavoriteState = !isFavoriteState }
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonFavoritePreview() {
    var isFavoriteState by remember { mutableStateOf(true) }
    FavoriteIconButton(
        isFavorite = isFavoriteState,
        onClick = { isFavoriteState = !isFavoriteState }
    )
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = Devices.PIXEL_6
)
@Composable
fun PrimaryButtonPreview(
) {
    PrimaryButton(
        onClick = {},
        text = stringResource(id = R.string.results_no_like_recipes_btn_text)
    )
}

