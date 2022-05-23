package andersen.two_clues.feature.puzzle.ui

import andersen.two_clues.R
import andersen.two_clues.feature.puzzle.model.PuzzleAction
import andersen.two_clues.feature.puzzle.model.PuzzleUiMessage
import andersen.two_clues.feature.puzzle.model.PuzzleViewState
import andersen.two_clues.feature.puzzle.presentation.PuzzleViewModel
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.findActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PuzzleUi(onBack: () -> Unit) {
    PuzzleUi(
        viewModel = hiltViewModel(),
        onBack = onBack
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun PuzzleUi(
    viewModel: PuzzleViewModel,
    onBack: () -> Unit
) {
    val viewState = viewModel.state.collectAsState()

    PuzzleUi(
        state = viewState.value,
        actioner = viewModel::submitAction,
        onBack = onBack,
        clearMessage = viewModel::clearMessage
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun PuzzleUi(
    state: PuzzleViewState,
    actioner: (PuzzleAction) -> Unit,
    onBack: () -> Unit,
    clearMessage: (id: Long) -> Unit
) {

    state.message?.let { message ->
        when (val m = message.message) {
            PuzzleUiMessage.ShowPuzzleErrorDialog -> {
                ShowFailDialog(clearMessage, message)
            }
            PuzzleUiMessage.ShowWinDialog -> {
                ShowWinDialog(clearMessage, message, onBack)
            }

            is PuzzleUiMessage.ShowRewardAd -> {
                actioner(
                    PuzzleAction.ShowRewordAd(
                        requireNotNull(LocalContext.current.findActivity()),
                    )
                )
                clearMessage(message.id)
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
private fun ShowWinDialog(
    clearMessage: (id: Long) -> Unit,
    message: UiMessage<PuzzleUiMessage>,
    onBack: () -> Unit
) {
    AlertDialog(onDismissRequest = {
        clearMessage(message.id)
        onBack()
    }, buttons = {}, title = {

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_correct),
                contentDescription = ""
            )
        }

    }, text = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.congratulations),
                style = MaterialTheme.typography.caption,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.you_completed_puzzle),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    })
}

@Composable
private fun ShowFailDialog(
    clearMessage: (id: Long) -> Unit,
    message: UiMessage<PuzzleUiMessage>
) {
    AlertDialog(onDismissRequest = {
        clearMessage(message.id)
    }, buttons = {}, title = {

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_wrong),
                contentDescription = ""
            )
        }

    }, text = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.oops),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                fontSize = 34.sp
            )

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.check_answers),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    })
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PuzzlePreview() {
    PuzzleUi {}
}