package andersen.two_clues.data.puzzles

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import andersen.two_clues.data.common.model.PuzzleName

private val Context.puzzleAvailabilityDataStore: DataStore<Preferences> by preferencesDataStore(name = "puzzleAvailabilityDataStore")
private val Context.puzzleCompleteDataStore: DataStore<Preferences> by preferencesDataStore(name = "puzzleCompleteDataStore")

class PuzzleDataStoreImp(val context: Context) : PuzzleDataStore {

    override suspend fun makeAvailable(name: PuzzleName) {
        context.puzzleAvailabilityDataStore.edit { prefs ->
            prefs[booleanPreferencesKey(name.name)] = true
        }
    }

    override fun isAvailable(name: PuzzleName): Flow<Boolean> {
        return context.puzzleAvailabilityDataStore.data
            .map { prefs ->
                prefs[booleanPreferencesKey(name.name)] ?: false
            }
    }

    override suspend fun finishPuzzle(name: PuzzleName) {
        context.puzzleCompleteDataStore.edit { prefs ->
            prefs[booleanPreferencesKey(name.name)] = true
        }
    }

    override fun isFinished(name: PuzzleName): Flow<Boolean> {
        return context.puzzleCompleteDataStore.data
            .map { prefs ->
                prefs[booleanPreferencesKey(name.name)] ?: false
            }
    }
}