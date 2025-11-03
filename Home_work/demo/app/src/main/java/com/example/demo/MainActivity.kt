package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demo.ui.theme.DemoTheme // Đảm bảo import đúng theme của bạn
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // DemoTheme là chủ đề giao diện được tạo sẵn cho project của bạn.
            DemoTheme {
                // Surface là một container từ Material Design với màu nền mặc định.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gọi Composable chính của chúng ta
                    GreetingScreen()
                }
            }
        }
    }
}

// --- BẮT ĐẦU VIẾT COMPOSABLE CỦA BẠN Ở ĐÂY ---

/**
 * Một màn hình đơn giản để chào hỏi người dùng.
 * Bao gồm một ô nhập liệu và một nút bấm.
 */
@Composable
fun GreetingScreen() {
    // 1. Quản lý trạng thái (State) cho ô nhập liệu.
    // `remember` giúp giá trị này không bị reset khi UI vẽ lại.
    // `mutableStateOf` làm cho Compose theo dõi sự thay đổi của nó.
    var name by remember { mutableStateOf("") }

    // Column sắp xếp các thành phần con theo chiều dọc.
    Column(
        // Modifier để tùy chỉnh giao diện và hành vi.
        modifier = Modifier
            .fillMaxSize() // Lấp đầy toàn bộ không gian
            .padding(16.dp), // Thêm khoảng đệm 16dp
        // Căn chỉnh các thành phần con ra giữa theo chiều ngang và dọc.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. Hiển thị văn bản chào hỏi.
        // `if` được dùng để chỉ hiển thị lời chào khi tên không rỗng.
        if (name.isNotEmpty()) {
            Text(
                text = "Xin chào, $name!",
                style = MaterialTheme.typography.headlineMedium // Sử dụng style chữ có sẵn
            )
        } else {
            Text(
                text = "Vui lòng nhập tên của bạn",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Thêm một khoảng trống giữa các thành phần
        Spacer(modifier = Modifier.height(16.dp))

        // 3. Ô nhập liệu (TextField) để người dùng nhập tên.
        OutlinedTextField(
            value = name, // Giá trị hiện tại của ô nhập liệu
            onValueChange = { newName ->
                // Callback được gọi mỗi khi người dùng nhập chữ.
                // Cập nhật lại state `name` với giá trị mới.
                name = newName
            },
            label = { Text("Tên của bạn") } // Nhãn cho ô nhập liệu
        )
    }
}

/**
 * Sử dụng @Preview để xem trước Composable của bạn ngay trong Android Studio.
 * Bạn có thể mở tab "Split" hoặc "Design" để xem.
 */
@Preview(showBackground = true, name = "Greeting Screen Preview")
@Composable
fun GreetingScreenPreview() {
    DemoTheme {
        GreetingScreen()
    }
}
