import android.annotation.SuppressLint
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipefinder.ui.recipe.RecipeDetailsDestination
import com.example.recipefinder.ui.recipe.RecipeDetailsScreen
import com.example.recipefinder.ui.recipes.RecipeItemViewModel
import com.example.recipefinder.ui.recipes.RecipesDestination
import com.example.recipefinder.ui.recipes.RecipesScreen
import com.example.recipefinder.ui.recipes.RecipesViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
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
                composable<RecipesDestination> { backStackEntry ->
                    val recipesViewModel: RecipesViewModel = hiltViewModel(backStackEntry)

                    RecipesScreen(
                        viewModel = recipesViewModel,
                        onRecipeClick = { recipe ->
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("recipe", recipe)
                            navController.navigate(RecipeDetailsDestination)
                        }
                    )
                }

                composable<RecipeDetailsDestination> {
                    val recipe = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<RecipeItemViewModel>("recipe")
                        ?: return@composable

                    val parentEntry = remember { navController.getBackStackEntry(RecipesDestination::class) }
                    val recipesViewModel: RecipesViewModel = hiltViewModel(parentEntry)

                    RecipeDetailsScreen(
                        recipe = recipe,
                        onBackClick = { navController.navigateUp() },
                        recipeViewModel = recipesViewModel
                    )
                }
            }
        }
    }
}