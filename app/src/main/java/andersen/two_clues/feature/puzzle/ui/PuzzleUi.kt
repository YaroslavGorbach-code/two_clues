package andersen.two_clues.feature.puzzle.ui

import andersen.two_clues.R
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColor
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColorLight
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundHinted
import andersen.two_clues.feature.puzzle.model.PuzzleAction
import andersen.two_clues.feature.puzzle.model.PuzzleUiMessage
import andersen.two_clues.feature.puzzle.model.PuzzleViewState
import andersen.two_clues.feature.puzzle.presentation.PuzzleViewModel
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.findActivity
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            PuzzleUiMessage.ShowAdWarningDialog -> {
                ShowAdWarningDialog(clearMessage, message, actioner)
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(onBack, state)

            state.currentTask?.let { task ->
                Task(
                    Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(16.dp)
                        .background(getOnBackgroundColor(), RoundedCornerShape(4)),
                    task,
                    currentTaskNumber = task.orderNumber,
                    maxTasksNumber = state.maxTasks
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (state.isAnswerCorrectVisible) {
                        AnswerCorrect()
                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    if (state.isAnswerInCorrectVisible) {
                        AnswerIncorrect()
                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    state.currentTask?.let { task ->
                        AnswerLetters(Modifier.fillMaxWidth(), task, actioner)

                        VariantLetters(
                            Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp),
                            task,
                            actioner
                        )
                    }
                }

                BottomButtons(Modifier.padding(16.dp), state, actioner)
            }
        }
    }
}

@Composable
private fun BottomButtons(
    modifier: Modifier,
    state: PuzzleViewState,
    actioner: (PuzzleAction) -> Unit
) {

    Column(modifier = modifier) {
        if (state.isAnswerCorrectVisible) {
            Button(
                shape = RoundedCornerShape(8),
                onClick = { actioner(PuzzleAction.NextTask) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getOnBackgroundColorLight())
            ) {
                Text(text = stringResource(id = R.string.continuee))
            }

        } else {
            Button(
                shape = RoundedCornerShape(8),
                onClick = { actioner(PuzzleAction.CheckAnswer) }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getOnBackgroundHinted())
            ) {
                Text(text = stringResource(id = R.string.check))
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                shape = RoundedCornerShape(8),
                onClick = { actioner(PuzzleAction.RevealWord) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = getOnBackgroundColorLight())
            ) {
                Text(text = stringResource(id = R.string.reveal_word))
            }

        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@ExperimentalFoundationApi
@Composable
private fun VariantLetters(
    modifier: Modifier,
    task: Puzzle.Task,
    actioner: (PuzzleAction) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(8),
        modifier = modifier
    ) {
        items(task.letters) { letter ->
            VariantCell(letter) {
                actioner(PuzzleAction.ChoseLetter(letter))
            }
        }
    }
}

@Composable
private fun AnswerLetters(modifier: Modifier, task: Puzzle.Task, actioner: (PuzzleAction) -> Unit) {
    Box(modifier = modifier) {
        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .align(Center)
        ) {

            itemsIndexed(task.correctAnswer) { index, correctAnswer ->

                if (correctAnswer.char.toString() == " ") {
                    Spacer(modifier = Modifier.size(20.dp))
                } else {
                    val myAnswer = task.myAnswer.getOrNull(index)

                    Log.i("dsdssd", "index $index")
                    Log.i("dsdssd", "myAnswer ${task.myAnswer}")

                    Log.i("dsdssd", "my answer ${myAnswer?.char}")
                    Log.i("dsdssd", "correct answer ${correctAnswer.char}")

                    AnswerCell(myAnswer, onClick = {
                        actioner(PuzzleAction.RemoveLetter(myAnswer))
                    })

                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
        }
    }
}

@Composable
private fun AnswerIncorrect() {
    Box(Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.align(Center)) {
            Text(
                text = stringResource(id = R.string.incorrect),
                style = MaterialTheme.typography.body2
            )
            Icon(
                Icons.Default.HighlightOff,
                contentDescription = "",
                tint = Color.Red,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun AnswerCorrect() {
    Box(Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.align(Center)) {
            Text(
                text = stringResource(id = R.string.correct),
                style = MaterialTheme.typography.body2
            )
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "",
                tint = Color.Green,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun Task(
    modifier: Modifier,
    task: Puzzle.Task,
    maxTasksNumber: Int,
    currentTaskNumber: Int
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "$currentTaskNumber of $maxTasksNumber",
            modifier = Modifier
                .align(TopEnd)
                .padding(8.dp),
            style = MaterialTheme.typography.caption,
            fontSize = 16.sp
        )

        Column(modifier = Modifier.align(Center)) {
            Text(
                text = task.clues.first,
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "----OR----",
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.body2,
                fontSize = 18.sp
            )
            Text(
                text = task.clues.second,
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.caption
            )
        }

    }
}

@Composable
private fun Toolbar(
    onBack: () -> Unit,
    state: PuzzleViewState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
                .clickable { onBack() }
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(start = 16.dp),
            text = stringResource(
                id =
                state.puzzle?.name?.resId ?: R.string.app_name
            ),
            style = MaterialTheme.typography.caption
        )
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
private fun ShowAdWarningDialog(
    clearMessage: (id: Long) -> Unit,
    message: UiMessage<PuzzleUiMessage>,
    actioner: (PuzzleAction) -> Unit
) {
    AlertDialog(onDismissRequest = {
        clearMessage(message.id)
    }, buttons = {
        Button(
            shape = RoundedCornerShape(8),
            onClick = {
                actioner(PuzzleAction.RequestShowRewordAd)
                clearMessage(message.id)
            }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = getOnBackgroundHinted())
        ) {
            Text(text = stringResource(id = R.string.watch_ads))
        }

    }, title = {}, text = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.reveal_letter_message),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
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