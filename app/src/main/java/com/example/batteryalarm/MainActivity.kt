package com.example.batteryalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.batteryalarm.ui.BatteryViewModel
import com.example.batteryalarm.ui.theme.BatteryAlarmTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = BatteryViewModel(this)
        setContent {
            BatteryAlarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    battery(modifier = Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

@Composable
fun battery(modifier: Modifier = Modifier, viewModel: BatteryViewModel = viewModel()) {

    val batteryState by viewModel.uiState.collectAsState()

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = batteryState),
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun batteryPreview() {
    battery()
}
