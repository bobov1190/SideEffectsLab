package uz.ttpu.sideeffectslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import uz.ttpu.sideeffectslab.task1.Task1Screen
import uz.ttpu.sideeffectslab.ui.theme.SideEffectsLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SideEffectsLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Task1Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
