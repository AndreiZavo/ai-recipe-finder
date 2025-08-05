package com.example.recipefinder.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.components.AsyncImageWrapper
import com.example.recipefinder.ui.components.FavoriteIconButton
import com.example.recipefinder.ui.components.StatusBarsAppearance
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.utils.ShowOnAppearanceToolbar
import com.example.recipefinder.ui.utils.formatDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeTitle: String,
    recipeDuration: Int,
    recipeImageUrl: String,
    recipeIngredients: List<String>,
    recipeInstructions: List<String>,
    onBackClick: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val scrollState = rememberScrollState()
    val showTopBar by remember { derivedStateOf { scrollState.value > screenHeight / 2 } }

    val configuration = LocalConfiguration.current
    val headerHeight = remember { configuration.screenHeightDp.dp * .6f }

    StatusBarsAppearance(lightStatusBars = showTopBar)

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Box {
                AsyncImageWrapper(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                    imageUrl = recipeImageUrl,
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 15.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = recipeTitle,
                        style = AppTextStyles.semibold,
                        fontSize = 24.sp,
                        color = AppColors.TextPrimary
                    )

                    Text(
                        text = formatDuration(recipeDuration),
                        style = AppTextStyles.regular,
                        fontSize = 14.sp,
                        color = AppColors.TextPrimary
                    )
                }

                FavoriteIconButton(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    isFavorite = false,
                    onClick = {}
                )
            }

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt tortor aliquam. Nunc consequat interdum varius sit amet mattis vulputate enim. Massa ultricies mi quis hendrerit dolor magna eget est lorem. Ac tortor dignissim convallis aenean et tortor at risus viverra. Eget nunc lobortis mattis aliquam faucibus purus in. Gravida cum sociis natoque penatibus et magnis dis parturient montes. Tellus elementum sagittis vitae et leo duis ut diam. Pretium quam vulputate dignissim suspendisse in est. Sed adipiscing diam donec adipiscing tristique risus nec feugiat. Erat nam at lectus urna duis convallis convallis tellus id. Faucibus purus in massa tempor nec feugiat. Nisl tincidunt eget nullam non nisi est sit amet. Bibendum est ultricies integer quis auctor elit sed. Vitae congue eu consequat ac felis donec et odio.Amet justo donec enim diam vulputate ut pharetra sit. Risus ultricies tristique nulla aliquet enim tortor at. Massa tincidunt dui ut ornare lectus sit. Eu sem integer vitae justo eget magna fermentum. Egestas erat imperdiet sed euismod nisi porta lorem mollis. Tortor at auctor urna nunc id cursus metus. At consectetur lorem donec massa sapien faucibus et molestie. Convallis tellus id interdum velit laoreet id donec ultrices. In egestas erat imperdiet sed euismod nisi porta. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Viverra justo nec ultrices dui sapien eget mi proin. Sed libero enim sed faucibus turpis in eu mi bibendum. Tincidunt augue interdum velit euismod in pellentesque massa placerat.Integer quis auctor elit sed vulputate mi sit. Commodo nulla facilisi nullam vehicula ipsum a arcu cursus. Sed vulputate mi sit amet mauris commodo quis imperdiet massa. Scelerisque fermentum dui faucibus in ornare quam viverra. In cursus turpis massa tincidunt dui ut ornare lectus sit. Aliquam sem et tortor consequat id porta. Et malesuada fames ac turpis egestas integer. Amet consectetur adipiscing elit pellentesque habitant morbi tristique. Quam pellentesque nec nam aliquam sem et tortor consequat id. Leo vel fringilla est ullamcorper eget nulla facilisi etiam dignissim.Ultricies mi quis hendrerit dolor magna eget est lorem. Vulputate dignissim suspendisse in est ante in. Eget sit amet tellus cras adipiscing enim eu turpis egestas. Nunc sed velit dignissim sodales ut eu sem integer vitae. Cursus risus at ultrices mi tempus imperdiet nulla malesuada. Mauris pharetra et ultrices neque ornare aenean euismod elementum nisi. Et netus et malesuada fames ac turpis egestas. Nibh praesent tristique magna sit amet purus gravida quis. Nisl purus in mollis nunc sed id semper. Magna etiam tempor orci eu lobortis elementum nibh.Et sollicitudin ac orci phasellus egestas tellus rutrum tellus. Erat velit scelerisque in dictum non consectetur a. Ac turpis egestas maecenas pharetra convallis posuere morbi leo. Sit amet consectetur adipiscing elit ut aliquam purus sit amet. Dui faucibus in ornare quam viverra orci sagittis eu. Id diam vel quam elementum pulvinar etiam non. Non diam phasellus vestibulum lorem sed risus ultricies tristique nulla. Sagittis purus sit amet volutpat consequat mauris. Feugiat nibh sed pulvinar proin gravida hendrerit lectus. Mattis nunc sed blandit libero volutpat sed cras. Dictumst quisque sagittis purus sit amet volutpat. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum.Porttitor eget dolor morbi non arcu risus quis varius. Enim lobortis scelerisque fermentum dui faucibus in ornare. Sit amet justo donec enim diam vulputate ut. Nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit amet. Nulla facilisi etiam dignissim diam quis enim lobortis. Dignissim cras tincidunt lobortis feugiat vivamus at. Eu feugiat pretium nibh ipsum consequat nisl vel pretium. Gravida arcu ac tortor dignissim convallis aenean et. Faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing. Condimentum lacinia quis vel eros donec ac. Ac odio tempor orci dapibus ultrices in iaculis.Eu consequat ac felis donec et odio pellentesque diam volutpat. Velit euismod in pellentesque massa placerat duis ultricies. Blandit aliquam etiam erat velit scelerisque in dictum non. Cursus mattis molestie a iaculis at erat pellentesque. Velit sed ullamcorper morbi tincidunt ornare massa eget egestas. Aliquet risus feugiat in ante metus dictum at tempor. Nunc sed augue lacus viverra vitae congue eu consequat ac. Purus sit amet luctus venenatis lectus magna fringilla urna porttitor. Lacus vestibulum sed arcu non odio euismod lacinia. Congue mauris rhoncus aenean vel elit scelerisque. Malesuada fames ac turpis egestas sed tempus urna. Faucibus turpis in eu mi bibendum neque egestas congue.Urna molestie at elementum eu facilisis sed odio morbi. Nisi est sit amet facilisis magna etiam tempor orci. Sed enim ut sem viverra aliquet eget sit amet. Vitae suscipit tellus mauris a diam maecenas sed enim. Aliquet bibendum enim facilisis gravida neque convallis a. Morbi enim nunc faucibus a pellentesque sit amet porttitor. Orci porta non pulvinar neque laoreet suspendisse interdum. Dignissim sodales ut eu sem integer vitae justo eget. Ut morbi tincidunt augue interdum velit euismod in pellentesque massa. Libero justo laoreet sit amet cursus sit amet dictum. Amet nisl suscipit adipiscing bibendum est ultricies integer quis.Lacus sed turpis tincidunt id aliquet risus feugiat. Tellus orci ac auctor augue mauris augue neque. Ut pharetra sit amet aliquam id diam maecenas ultricies. Augue interdum velit euismod in pellentesque massa placerat duis. Pellentesque habitant morbi tristique senectus et netus et malesuada. Viverra nam libero justo laoreet sit amet cursus. Pretium lectus quam id leo in vitae turpis massa. Mauris vitae ultricies leo integer malesuada nunc vel. Risus commodo viverra maecenas accumsan lacus vel facilisis. Enim ut tellus elementum sagittis vitae et leo duis. Diam maecenas ultricies mi eget mauris pharetra et. Posuere sollicitudin aliquam ultrices sagittis orci a scelerisque.Rutrum quisque non tellus orci ac auctor augue. Amet volutpat consequat mauris nunc congue nisi. Consectetur adipiscing elit duis tristique sollicitudin nibh sit amet. Neque convallis a cras semper auctor neque vitae tempus. Sed augue lacus viverra vitae congue eu. In tellus integer feugiat scelerisque varius morbi enim nunc. Adipiscing elit ut aliquam purus sit amet luctus. Pharetra diam sit amet nisl suscipit. Massa id neque aliquam vestibulum morbi blandit cursus risus. Eget dolor morbi non arcu risus quis varius quam. Fermentum iaculis eu non diam phasellus vestibulum lorem sed.Volutpat commodo sed egestas egestas fringilla phasellus faucibus. Sit amet dictum sit amet justo donec enim diam. Nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Convallis posuere morbi leo urna molestie at. Semper risus in hendrerit gravida rutrum quisque non. Ornare arcu odio ut sem nulla pharetra diam sit. Ultrices gravida dictum fusce ut placerat orci nulla pellentesque. Tortor posuere ac ut consequat semper viverra. Mi proin sed libero enim sed faucibus turpis in. Cras fermentum odio eu feugiat pretium nibh ipsum consequat.Odio morbi quis commodo odio aenean sed adipiscing. Tellus mauris a diam maecenas sed enim ut. Facilisis leo vel fringilla est ullamcorper eget nulla. Non curabitur gravida arcu ac tortor dignissim. Semper auctor neque vitae tempus quam pellentesque nec. Quisque egestas diam in arcu cursus euismod quis. Feugiat in fermentum posuere urna nec tincidunt praesent. Egestas quis ipsum suspendisse ultrices gravida dictum. Amet porttitor eget dolor morbi non arcu risus quis. Aliquam vestibulum morbi blandit cursus risus at ultrices mi. Bibendum enim facilisis gravida neque convallis a cras.Velit aliquet sagittis id consectetur purus ut faucibus pulvinar. Arcu dictum varius duis at consectetur lorem donec massa sapien. Eu nisl nunc mi ipsum faucibus vitae aliquet. Ultrices tincidunt arcu non sodales neque sodales ut etiam. At augue eget arcu dictum varius duis. Id cursus metus aliquam eleifend mi in nulla. Enim praesent elementum facilisis leo vel fringilla est. Suspendisse potenti nullam ac tortor vitae purus faucibus. Netus et malesuada fames ac turpis egestas. Habitasse platea dictumst quisque sagittis purus sit.Massa tempor nec feugiat nisl pretium fusce id velit. Diam phasellus vestibulum lorem sed risus ultricies. Aliquam malesuada bibendum arcu vitae elementum curabitur vitae. Est placerat in egestas erat imperdiet sed. Quis lectus nulla at volutpat diam ut. Nec sagittis aliquam malesuada bibendum arcu vitae elementum. Nunc scelerisque viverra mauris in aliquam sem fringilla ut. Elementum eu facilisis sed odio morbi quis commodo. Tellus rutrum tellus pellentesque eu tincidunt. Tristique senectus et netus et malesuada fames ac turpis.Ac felis donec et odio pellentesque diam volutpat. Nisi lacus sed viverra tellus in hac. Mattis rhoncus urna neque viverra justo nec ultrices. A cras semper auctor neque vitae tempus quam. Erat nam at lectus urna duis convallis. Faucibus purus in massa tempor nec feugiat nisl. Nisl tincidunt eget nullam non nisi est. Bibendum est ultricies integer quis auctor. Vitae congue eu consequat ac felis. Amet justo donec enim diam vulputate.Risus ultricies tristique nulla aliquet enim. Massa tincidunt dui ut ornare lectus. Eu sem integer vitae justo eget. Egestas erat imperdiet sed euismod. Tortor at auctor urna nunc id. At consectetur lorem donec massa. Convallis tellus id interdum velit. In egestas erat imperdiet sed. Suspendisse sed nisi lacus sed. Viverra justo nec ultrices dui. Sed libero enim sed faucibus. Tincidunt augue interdum velit euismod.Integer quis auctor elit sed. Commodo nulla facilisi nullam vehicula. Sed vulputate mi sit amet. Scelerisque fermentum dui faucibus in. In cursus turpis massa tincidunt. Aliquam sem et tortor consequat. Et malesuada fames ac turpis. Amet consectetur adipiscing elit. Quam pellentesque nec nam aliquam. Leo vel fringilla est ullamcorper.Ultricies mi quis hendrerit dolor. Vulputate dignissim suspendisse in est. Eget sit amet tellus cras. Nunc sed velit dignissim sodales. Cursus risus at ultrices mi. Mauris pharetra et ultrices neque. Et netus et malesuada fames. Nibh praesent tristique magna sit. Nisl purus in mollis nunc. Magna etiam tempor orci eu.Et sollicitudin ac orci phasellus. Erat velit scelerisque in dictum. Ac turpis egestas maecenas pharetra. Sit amet consectetur adipiscing elit. Dui faucibus in ornare quam. Id diam vel quam elementum. Non diam phasellus vestibulum lorem. Sagittis purus sit amet volutpat. Feugiat nibh sed pulvinar proin. Mattis nunc sed blandit libero. Dictumst quisque sagittis purus sit. Tempus imperdiet nulla malesuada pellentesque.",
                style = AppTextStyles.regular
            )
        }

        ShowOnAppearanceToolbar(
            onBackClick = onBackClick,
            show = showTopBar,
            title = recipeTitle
        )
    }
}