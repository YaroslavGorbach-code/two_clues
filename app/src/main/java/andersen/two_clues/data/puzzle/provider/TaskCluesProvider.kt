package andersen.two_clues.data.puzzle.provider

import andersen.two_clues.data.common.model.PuzzleName
import android.content.Context

class TaskCluesProvider(
    private val context: Context,
    private val taskIdProvider: TaskCuesResIdProvider
) {

    companion object {
        private const val TASKS_SPLIT_SYMBOL = "|"
        private const val TASK_SPLIT_SYMBOL = "or"
    }

    fun provider(name: PuzzleName): List<Pair<String, String>> {
        return context.resources.getString(taskIdProvider.provide(name))
            .split(TASKS_SPLIT_SYMBOL)
            .map { it.split(TASK_SPLIT_SYMBOL) }.map { it[0] to it[1] }
    }
}
