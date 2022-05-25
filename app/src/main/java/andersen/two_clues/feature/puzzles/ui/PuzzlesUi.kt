package andersen.two_clues.feature.puzzles.ui

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColor
import andersen.two_clues.feature.puzzles.model.PuzzlesAction
import andersen.two_clues.feature.puzzles.model.PuzzlesUiMessage
import andersen.two_clues.feature.puzzles.model.PuzzlesViewState
import andersen.two_clues.feature.puzzles.presentation.PuzzlesViewModel
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.findActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PuzzlesUi(navigateToPuzzle: (name: PuzzleName) -> Unit) {
    PuzzlesUi(viewModel = hiltViewModel(), navigateToPuzzle = navigateToPuzzle)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun PuzzlesUi(
    viewModel: PuzzlesViewModel,
    navigateToPuzzle: (name: PuzzleName) -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    PuzzlesUi(
        state = viewState.value,
        actioner = viewModel::submitAction,
        navigateToPuzzle = navigateToPuzzle,
        clearMessage = viewModel::clearMessage
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun PuzzlesUi(
    state: PuzzlesViewState,
    actioner: (PuzzlesAction) -> Unit,
    navigateToPuzzle: (name: PuzzleName) -> Unit,
    clearMessage: (id: Long) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                actioner(PuzzlesAction.LoadPuzzle)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    state.message?.let { message ->
        when (message.message) {
            is PuzzlesUiMessage.ShowPuzzleUnAvailableDialog -> ShowUnavailableDialog(
                message.message.name,
                actioner,
                clearMessage,
                message
            )
            is PuzzlesUiMessage.ShowRewardAd -> {
                actioner(
                    PuzzlesAction.ShowRewordAd(
                        activity = requireNotNull(LocalContext.current.findActivity()),
                        puzzleName = message.message.puzzleName
                    )
                )
                clearMessage(message.id)
            }
            PuzzlesUiMessage.ShowHelpDialog -> {
                ShowHelpDialogDialog(
                    clearMessage = clearMessage,
                    message = message,
                )
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Row {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.caption,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                )

                Icon(
                    Icons.Default.HelpOutline,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .clickable {
                            actioner(PuzzlesAction.ShowHelpDialog)
                        }
                )
            }

            Text(
                text = stringResource(id = R.string.app_description),
                modifier = Modifier.padding(bottom = 32.dp, start = 8.dp, end = 8.dp),
            )

            LazyColumn {
                items(state.puzzleItems) { item ->
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = getOnBackgroundColor())
                    )

                    PuzzleItemUi(item = item, onPuzzle = {
                        navigateToPuzzle(item.name)
                    }, showUnavailableDialog = {
                        actioner(PuzzlesAction.ShowPuzzleUnAvailableDialog(item.name))
                    })
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(color = getOnBackgroundColor())
            )
        }

    }
}

@Composable
private fun ShowUnavailableDialog(
    name: PuzzleName,
    actioner: (PuzzlesAction) -> Unit,
    clearMessage: (id: Long) -> Unit,
    message: UiMessage<PuzzlesUiMessage>
) {
    AlertDialog(onDismissRequest = {
        clearMessage(message.id)
    }, buttons = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    actioner(
                        PuzzlesAction.RequestShowRewordAd(
                            name
                        )
                    )
                    clearMessage(message.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(CenterHorizontally),
            ) {
                Text(text = stringResource(id = R.string.view_ads), color = Color.White)
            }
        }

    }, title = {

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_lock),
                contentDescription = ""
            )
        }

    }, text = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.puzzle_is_unavailable),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.puzzle_is_unavailable_explanation),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    })
}

@Composable
private fun ShowHelpDialogDialog(
    clearMessage: (id: Long) -> Unit,
    message: UiMessage<PuzzlesUiMessage>
) {
    val scroll = rememberScrollState(0)

    AlertDialog(onDismissRequest = {
        clearMessage(message.id)
    }, buttons = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {

                    clearMessage(message.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(CenterHorizontally),
            ) {

            }
        }

    }, title = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll)
        ) {

        }
    })
}