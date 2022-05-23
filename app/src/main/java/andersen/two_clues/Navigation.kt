package andersen.two_clues

import andersen.two_clues.data.common.model.PuzzleName
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.coroutines.InternalCoroutinesApi


const val PUZZLE_NAME_ARG = "PUZZLE_NAME_ARG"

sealed class Screen(val route: String) {
    object Puzzles : Screen("Puzzles")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Puzzles : LeafScreen("Puzzles")

    object Puzzle : LeafScreen("Puzzle/{${PUZZLE_NAME_ARG}}") {
        fun createRoute(root: Screen, puzzleName: PuzzleName): String {
            return "${root.route}/Puzzle/$puzzleName"
        }
    }
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Puzzles.route,
        modifier = modifier,
    ) {
        addPuzzlesTopLevel(navController)
    }
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addPuzzlesTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Puzzles.route,
        startDestination = LeafScreen.Puzzles.createRoute(Screen.Puzzles),
    ) {
        addPuzzles(navController, Screen.Puzzles)
        addPuzzle(navController, Screen.Puzzles)

    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addPuzzles(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Puzzles.createRoute(root)) {
//        PuzzlesUi { name ->
//            navController.navigate(
//                LeafScreen.Puzzle.createRoute(
//                    root = root,
//                    puzzleName = name
//                )
//            )
//        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addPuzzle(
    navController: NavController,
    root: Screen,
) {
    composable(
        LeafScreen.Puzzle.createRoute(root), arguments = listOf(
            navArgument(PUZZLE_NAME_ARG) {
                type = NavType.EnumType(PuzzleName::class.java)
            })
    ) { backStackEntry ->
//        PuzzleUi {
//            navController.popBackStack()
//        }
    }
}

