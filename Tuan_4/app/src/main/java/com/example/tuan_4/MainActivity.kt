package com.example.tuan_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Gọi hàm Composable chính, quản lý toàn bộ luồng của ứng dụng
        setContent { UthSmartTasksApp() }
    }
}
