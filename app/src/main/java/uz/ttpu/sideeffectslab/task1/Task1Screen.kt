package uz.ttpu.sideeffectslab.task1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val TAG = "Task1"
private const val SplashWaitTimeMillis = 2000L

/**
 * Variant 1 (BAD): captures the onTimeout lambda directly.
 */
@Composable
fun LandingScreenStale(onTimeout: () -> Unit) {
    LaunchedEffect(true) {
        delay(SplashWaitTimeMillis)
        onTimeout() // old lambda from the first composition
    }
    Text("Splash screen (captured lambda)")
}

/**
 * Variant 2 (GOOD): rememberUpdatedState.
 */
@Composable
fun LandingScreen(onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(true) {
        delay(SplashWaitTimeMillis)
        currentOnTimeout() // latest lambda
    }
    Text("Splash screen (rememberUpdatedState)")
}

@Composable
fun Task1Screen(modifier: Modifier = Modifier) {
    // Changing runId rebuilds everything, so the test can be repeated
    var runId by remember { mutableIntStateOf(0) }
    key(runId) {
        Task1Content(onRestart = { runId++ }, modifier = modifier)
    }
}

@Composable
private fun Task1Content(onRestart: () -> Unit, modifier: Modifier = Modifier) {
    var version by remember { mutableIntStateOf(0) }
    var staleResult by remember { mutableStateOf("waiting...") }
    var updatedResult by remember { mutableStateOf("waiting...") }

    // version counter: +1 every 500 ms
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            version++
        }
    }

    // Copy the value, so every lambda remembers its own version number
    val v = version
    val onTimeoutStale: () -> Unit = {
        Log.d(TAG, "captured lambda -> onTimeout v$v")
        staleResult = "called v$v"
    }
    val onTimeoutUpdated: () -> Unit = {
        Log.d(TAG, "rememberUpdatedState -> onTimeout v$v")
        updatedResult = "called v$v"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Task 1. Splash screen", style = MaterialTheme.typography.titleLarge)
        Text("Current version: v$version")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LandingScreenStale(onTimeout = onTimeoutStale)
                Text("Result: $staleResult")
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LandingScreen(onTimeout = onTimeoutUpdated)
                Text("Result: $updatedResult")
            }
        }

        Button(
            onClick = onRestart,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Restart")
        }
    }
}
