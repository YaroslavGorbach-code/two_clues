package andersen.two_clues.di.data

import andersen.two_clues.data.common.PuzzleRepo
import andersen.two_clues.data.common.PuzzleRepoImp
import andersen.two_clues.data.puzzle.factory.PuzzleFactory
import andersen.two_clues.data.puzzle.factory.TasksFactory
import andersen.two_clues.data.puzzle.mapper.AnswerToLettersMapper
import andersen.two_clues.data.puzzle.provider.TaskAnswersProvider
import andersen.two_clues.data.puzzle.provider.TaskCluesProvider
import andersen.two_clues.data.puzzle.provider.TaskCuesResIdProvider
import andersen.two_clues.data.puzzles.PuzzleDataStore
import andersen.two_clues.data.puzzles.PuzzleDataStoreImp
import andersen.two_clues.data.puzzles.factory.PuzzleAvailabilityProvider
import andersen.two_clues.data.puzzles.factory.PuzzleCompleteProvider
import andersen.two_clues.data.puzzles.factory.PuzzleDescriptionProvider
import andersen.two_clues.data.puzzles.factory.PuzzlesFactory
import andersen.two_clues.utills.AdManager
import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataPuzzleModule {

    @Provides
    fun provideAdManagerFactory(app: Application): AdManager {
        return AdManager(app)
    }

    @Provides
    fun providePuzzleAvailabilityDataStore(app: Application): PuzzleDataStore {
        return PuzzleDataStoreImp(app)
    }

    @Provides
    fun providePuzzleCompleteDataStore(puzzleDataStore: PuzzleDataStore): PuzzleCompleteProvider {
        return PuzzleCompleteProvider(puzzleDataStore)
    }

    @Provides
    fun providePuzzleTaskCluesProvider(
        app: Application,
        taskCuesResIdProvider: TaskCuesResIdProvider
    ): TaskCluesProvider {
        return TaskCluesProvider(app, taskCuesResIdProvider)
    }

    @Provides
    fun providePuzzleTaskAnswerProvider(app: Application): TaskAnswersProvider {
        return TaskAnswersProvider(app)
    }

    @Provides
    fun provideTaskFactory(
        puzzleAnswerToLettersMapper: AnswerToLettersMapper,
        taskCluesProvider: TaskCluesProvider,
        puzzleTaskAnswersProvider: TaskAnswersProvider
    ): TasksFactory {
        return TasksFactory(
            puzzleAnswerToLettersMapper = puzzleAnswerToLettersMapper,
            taskCluesProvider = taskCluesProvider,
            puzzleTaskAnswersProvider = puzzleTaskAnswersProvider,
        )
    }

    @Provides
    fun providePuzzleFactory(tasksFactory: TasksFactory): PuzzleFactory {
        return PuzzleFactory(tasksFactory)
    }

    @Provides
    fun provideTaskResIdProvider(): TaskCuesResIdProvider {
        return TaskCuesResIdProvider()
    }

    @Provides
    fun provideAnswersToLettersMapper(): AnswerToLettersMapper {
        return AnswerToLettersMapper()
    }

    @Provides
    fun providePuzzleAvailabilityProvider(puzzleDataStore: PuzzleDataStore): PuzzleAvailabilityProvider {
        return PuzzleAvailabilityProvider(puzzleDataStore)
    }

    @Provides
    fun providePuzzleStoryProvider(context: Application): PuzzleDescriptionProvider {
        return PuzzleDescriptionProvider(context)
    }

    @Provides
    fun providePuzzlesFactory(
        storyProvider: PuzzleDescriptionProvider,
        puzzleAvailabilityProvider: PuzzleAvailabilityProvider,
        puzzleCompleteProvider: PuzzleCompleteProvider
    ): PuzzlesFactory {
        return PuzzlesFactory(
            puzzleDescriptionProvider = storyProvider,
            puzzleAvailabilityProvider = puzzleAvailabilityProvider,
            puzzleCompleteProvider = puzzleCompleteProvider
        )
    }

    @Singleton
    @Provides
    fun providePuzzleRepo(
        factory: PuzzleFactory,
        puzzlesFactory: PuzzlesFactory,
        puzzleDataStore: PuzzleDataStore
    ): PuzzleRepo {
        return PuzzleRepoImp(factory, puzzlesFactory, puzzleDataStore)
    }
}