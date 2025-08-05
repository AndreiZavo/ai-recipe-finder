package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.recipefinder.R
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles

sealed interface DialogButton {
    val text: String
    val onClick: () -> Unit
}

data class PositiveDialogButton(
    override val text: String,
    val color: Color = AppColors.MainAccent,
    override val onClick: () -> Unit
) : DialogButton

data class NegativeDialogButton(
    override val text: String,
    override val onClick: () -> Unit
) : DialogButton

@Composable
fun GenericDialog(
    title: String,
    positiveButton: PositiveDialogButton,
    negativeButton: NegativeDialogButton? = null,
    message: String? = null,
    onDismissRequest: () -> Unit,
    image: (@Composable ColumnScope.() -> Unit)? = null,
    closeIcon: (@Composable BoxScope.() -> Unit)? = null,
) {
    BaseDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        message = message,
        image = image,
        closeIcon = closeIcon,
        positiveButton = positiveButton,
        negativeButton = negativeButton
    )
}

@Composable
private fun BaseDialog(
    title: String,
    positiveButton: PositiveDialogButton,
    negativeButton: NegativeDialogButton? = null,
    message: String? = null,
    onDismissRequest: () -> Unit,
    image: (@Composable ColumnScope.() -> Unit)? = null,
    closeIcon: (@Composable BoxScope.() -> Unit)? = null,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .background(AppColors.Primary, shape = RoundedCornerShape(12.dp))
                    .padding(top = 42.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (image != null) {
                    image()
                }

                Text(
                    modifier = Modifier.padding(top = if (image != null) 24.dp else 0.dp),
                    text = title,
                    style = AppTextStyles.bold,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    color = AppColors.TextPrimary,
                    textAlign = TextAlign.Center
                )

                if (!message.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(horizontal = 28.dp),
                        text = message,
                        style = AppTextStyles.regular,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = AppColors.TextPrimary,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = positiveButton.onClick,
                        text = positiveButton.text,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = positiveButton.color,
                            contentColor = Color.White,
                        )
                    )

                    if (negativeButton != null) {
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = negativeButton.onClick,
                        ) {
                            Text(
                                text = negativeButton.text,
                                style = AppTextStyles.regular,
                            )
                        }
                    }
                }
            }

            if (closeIcon != null) {
                closeIcon()
            }
        }
    }
}

@Preview
@Composable
private fun GenericDialogPreview() {
    GenericDialog(
        onDismissRequest = {},
        title = "Title",
        message = "Message",
        image = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImageWrapper(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9),
                    imageUrl = "https://images.unsplash.com/photo-1719268921855-d2897ed6e91f?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90b3MtZmVlZHw1Nnx8fGVufDB8fHx8fA%3D%3D",
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.img_placeholder)
                )
            }
        },
        closeIcon = {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, AppColors.Background, RoundedCornerShape(12.dp))
                    .background(AppColors.Primary)
                    .padding(10.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black
            )
        },
        positiveButton = PositiveDialogButton("Positive") {},
        negativeButton = NegativeDialogButton("Negative") {}
    )
}