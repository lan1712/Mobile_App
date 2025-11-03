package com.example.smart_uth_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.smart_uth_app.navigation.AppNavGraph
import com.example.smart_uth_app.ui.theme.Smart_UTH_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Smart_UTH_AppTheme {
                val nav = rememberNavController()
                AppNavGraph(navController = nav)
            }
        }
    }
}
