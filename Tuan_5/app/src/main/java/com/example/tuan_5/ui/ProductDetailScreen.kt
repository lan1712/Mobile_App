package com.example.tuan_5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import com.example.tuan_5.R


@Composable
fun ProductDetailScreen() {
//    // dữ liệu fake (hardcode demo UI trước)
//    val productName =
//        "Giày Nike Nam Nữ Chính Hãng - Nike Air Force 1 '07 LVB - Màu Trắng | JapanSport HF2898-100"
//    val productPrice = "4.000.000đ"
//    val productDesc = '''
//        Về giày chạy bộ, trọng lượng đôi quan trọng. Đó là lý do tại sao đôi giày này LIGHTRACER PRO nhẹ hơn so với những bản trước.
//        Mũi foam đặc siêu nhẹ và nhựa siêu mỏng này giúp đem lại hiệu suất tối đa cho chân của bạn nhờ năng lượng trong từng cú bật.
//        Lớp lót thoáng khí, thân giày bọc ôm lấy bàn chân, giữ ổn định khi di chuyển nhanh.
//
//        Thiết kế hiện đại kết hợp cùng công nghệ đệm mới giúp trải nghiệm thoải mái ngay cả khi chạy đường dài, đồng thời vẫn có độ bền phù hợp cho việc sử dụng hàng ngày.
//    '''.trimIndent()

    // Toàn bộ màn hình cuộn được
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // Thanh top bar đơn giản
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* TODO: handle back */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Product detail",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Card bao sản phẩm
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                // Ảnh sản phẩm
                Image(
                    painter = painterResource(id = R.drawable.shoe_sample),
                    contentDescription = "Product image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tên sản phẩm
                Text(
                    text = productName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Giá
                Row {
                    Text(
                        text = "Giá: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = productPrice,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mô tả
                Text(
                    text = productDesc,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight
                )
            }
        }
    }
}
