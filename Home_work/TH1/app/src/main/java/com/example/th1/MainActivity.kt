package com.example.th1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Week2Screen() }
    }
}

@Composable
fun Week2Screen() {
    var name by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf<String?>(null) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text("THỰC HÀNH 01", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        // Ô nhập Họ và tên
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Họ và tên") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // Ô nhập Tuổi (chỉ số)
        OutlinedTextField(
            value = ageText,
            onValueChange = { ageText = it },
            label = { Text("Tuổi") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = error != null
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val age = ageText.trim().toIntOrNull()
                // validate
                when {
                    name.trim().isEmpty() -> {
                        error = "Vui lòng nhập họ và tên"
                        result = null
                    }
                    age == null -> {
                        error = "Tuổi phải là số nguyên"
                        result = null
                    }
                    age < 0 || age > 150 -> {
                        error = "Tuổi không hợp lệ (0–150)"
                        result = null
                    }
                    else -> {
                        error = null
                        val group = classifyAge(age)
                        result = "Xin chào ${name.trim()}, $age tuổi — Bạn là: $group"
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Kiểm tra")
        }

        if (error != null) {
            Spacer(Modifier.height(10.dp))
            Text(error!!, color = Color(0xFFD32F2F))
        }

        result?.let {
            Spacer(Modifier.height(16.dp))
            Surface(
                color = Color(0xFFE3F2FD),
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    it,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}


private fun classifyAge(age: Int): String = when {
    age < 2 -> "Em bé (<2)"
    age in 2..5 -> "Trẻ em (2–6)"
    age in 6..65 -> "Người lớn (6–65)"
    else -> "Người già (>65)"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewWeek2() {
    Week2Screen()
}
