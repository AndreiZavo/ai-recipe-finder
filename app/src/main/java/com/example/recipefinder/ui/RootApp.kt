import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recipefinder.ui.recipe.RecipeDetailsDestination
import com.example.recipefinder.ui.recipe.RecipeDetailsScreen
import com.example.recipefinder.ui.recipes.RecipesDestination
import com.example.recipefinder.ui.recipes.RecipesScreen


@Composable
fun RootApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(top = 0.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = RecipesDestination,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                composable<RecipesDestination> {
                    RecipesScreen(
                        onRecipeClick = { id ->
                            navController.navigate(RecipeDetailsDestination(id))
                        },
                        onFavoriteClick = {}
                    )
                }

                composable<RecipeDetailsDestination> {
                    val id = it.toRoute<RecipeDetailsDestination>().id

                    RecipeDetailsScreen(
                        recipeId = id
                    )
                }
            }
        }
    }
}