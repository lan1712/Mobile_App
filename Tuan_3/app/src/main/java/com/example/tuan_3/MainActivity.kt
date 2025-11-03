package com.example.tuan_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tuan_3.ui.theme.Tuan_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Tuan_3Theme { AppNav() } }
    }
}

/* ---------------- ROUTES ---------------- */
private object Route {
    const val Onboarding = "onboarding"
    const val List = "list"
    const val Text = "text"
    const val Images = "images"
    const val TextField = "textfield"
    const val Password = "password"
    const val Row = "row"
    const val Column = "column"
}

/* ---------------- NAVIGATION ---------------- */
@Composable
private fun AppNav(nav: NavHostController = rememberNavController()) {
    NavHost(nav, startDestination = Route.Onboarding) {

        // Màn Onboarding đầu tiên
        composable(Route.Onboarding) {
            OnboardingScreen(
                name = "Phạm Vũ Lân",
                studentId = "054205005878",
                onReady = {
                    nav.navigate(Route.List) {
                        popUpTo(Route.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.List) { ComponentsScreen(onItem = { nav.navigate(it) }) }
        composable(Route.Text) { SimpleTop("Text Detail", { nav.popBackStack() }) { TextDetailScreen() } }
        composable(Route.Images) { SimpleTop("Images", { nav.popBackStack() }) { ImagesScreen() } }
        composable(Route.TextField) { SimpleTop("TextField", { nav.popBackStack() }) { TextFieldScreen() } }
        composable(Route.Password) { SimpleTop("PasswordField", { nav.popBackStack() }) { PasswordFieldScreen() } }
        composable(Route.Row) { SimpleTop("Row Layout", { nav.popBackStack() }) { RowLayoutScreen() } }
        composable(Route.Column) { SimpleTop("Column Layout", { nav.popBackStack() }) { ColumnLayoutScreen() } }
    }
}

@Preview
@Composable
private fun AppNavPreview() {
    // nav parameter has a default value, so we don't need to pass it.
    AppNav()
}

/* ---------------- ONBOARDING SCREEN ---------------- */
@Composable
fun OnboardingScreen(
    name: String,
    studentId: String,
    onReady: () -> Unit
) {
    Surface(color = Color(0xFFF8F9FB)) {
        Box(Modifier.fillMaxSize()) {

            // Thông tin người dùng góc trên phải
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, end = 20.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = studentId,
                    color = Color(0xFF7A7D85),
                    fontSize = 13.sp
                )
            }

            // Ảnh + mô tả trung tâm
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.compose_logo),
                    contentDescription = "Jetpack Compose",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(28.dp))
                )

                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Jetpack Compose",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                    color = Color(0xFF6B6F76),
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(120.dp))
            }

            // Gradient + Nút “I’m ready”
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            1f to Color(0x66E9EDF5)
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF5AA3FF), Color(0xFF3F82FF))
                        )
                    )
                    .clickable { onReady() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I’m ready",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        name = "Phạm Vũ Lân",
        studentId = "054205005878",
        onReady = {}
    )
}

/* ---------------- UI COMPONENTS LIST ---------------- */
@Composable
fun ComponentsScreen(onItem: (String) -> Unit) {
    val blue = Color(0xFFD7ECFF)
    val red = Color(0xFFFFD2D2)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TitleCenter("UI Components List")

        SectionHeader("Display")
        ItemCard("Text", "Displays text", blue) { onItem(Route.Text) }
        ItemCard("Image", "Displays an image", blue) { onItem(Route.Images) }

        SectionHeader("Input")
        ItemCard("TextField", "Input field for text", blue) { onItem(Route.TextField) }
        ItemCard("PasswordField", "Input field for passwords", blue) { onItem(Route.Password) }

        SectionHeader("Layout")
        ItemCard("Column", "Arranges elements vertically", blue) { onItem(Route.Column) }
        ItemCard("Row", "Arranges elements horizontally", blue) { onItem(Route.Row) }

        ItemCard("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản", red) {}
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun ComponentsScreenPreview() {
    ComponentsScreen(onItem = {})
}

@Composable
private fun TitleCenter(text: String) =
    Text(
        text,
        color = Color(0xFF1B78FF),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )

@Preview
@Composable
private fun TitleCenterPreview() {
    TitleCenter(text = "UI Components List")
}

@Composable
private fun SectionHeader(text: String) =
    Text(
        text,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF555B66),
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

@Preview
@Composable
private fun SectionHeaderPreview() {
    SectionHeader(text = "Display")
}

@Composable
private fun ItemCard(title: String, subtitle: String, bg: Color, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(14.dp)
            .padding(vertical = 6.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF1E1E1E))
        Spacer(Modifier.height(2.dp))
        Text(subtitle, color = Color(0xFF5F6470))
    }
}

@Preview
@Composable
private fun ItemCardPreview() {
    ItemCard(
        title = "Text",
        subtitle = "Displays text",
        bg = Color(0xFFD7ECFF),
        onClick = {}
    )
}

/* ---------------- SIMPLE TOP BAR ---------------- */
@Composable
private fun SimpleTop(title: String, onBack: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "‹",
                fontSize = 26.sp,
                color = Color(0xFF1B78FF),
                modifier = Modifier.clickable { onBack() }.padding(end = 8.dp)
            )
            Text(title, color = Color(0xFF1B78FF), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
        Divider(color = Color(0x11000000))
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            content = content
        )
    }
}

@Preview
@Composable
private fun SimpleTopPreview() {
    SimpleTop(
        title = "Text Detail",
        onBack = {},
        content = { TextDetailScreen() }
    )
}

/* ---------------- DETAIL SCREENS ---------------- */
@Composable
private fun TextDetailScreen() {
    val demo = buildAnnotatedString {
        withStyle(SpanStyle(fontSize = 28.sp)) {
            append("The ")
            withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("qu") }
            append("i")
            withStyle(SpanStyle(letterSpacing = 6.sp)) { append("ck ") }
            withStyle(SpanStyle(color = Color(0xFFB37000), fontWeight = FontWeight.SemiBold)) { append("Brown\n") }
            append("fox j u m p s ")
            withStyle(SpanStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontWeight = FontWeight.SemiBold)) {
                append("over\n")
            }
            append("the ")
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)) {
                append("lazy")
            }
            append(" dog.")
        }
    }
    Text(demo, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
    Divider(Modifier.padding(top = 12.dp))
}

@Preview
@Composable
private fun TextDetailScreenPreview() {
    TextDetailScreen()
}

@Composable
private fun ImagesScreen() {
    val urlImage =
        "https://s.cmx-cdn.com/giaothongvantaitphcm.edu.vn/wp-content/uploads/2024/06/ky-niem-36-nam-thanh-lap-truong-dai-hoc-giao-thong-van-tai-tphcm-560px.jpg"

    AsyncImage(
        model = urlImage,
        contentDescription = "Kỷ niệm 36 năm thành lập trường",
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
    )
    Spacer(Modifier.height(6.dp))
    Text(urlImage, fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

    Spacer(Modifier.height(16.dp))

    Image(
        painter = painterResource(R.drawable.uth_campus),
        contentDescription = "Đại học GTVT TP.HCM",
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
    )
    Spacer(Modifier.height(6.dp))
    Text("UTH - Đại học Giao thông Vận tải TP.HCM", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
private fun ImagesScreenPreview() {
    ImagesScreen()
}

@Composable
private fun TextFieldScreen() {
    var text by remember { mutableStateOf("") }
    Spacer(Modifier.height(12.dp))
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Thông tin nhập") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(24.dp)
    )
    Spacer(Modifier.height(12.dp))
    Text("Tự động cập nhật dữ liệu theo textfield", color = Color(0xFFD32F2F))
    Spacer(Modifier.height(8.dp))
    Text("Bạn đã nhập: $text")
}

@Preview
@Composable
private fun TextFieldScreenPreview() {
    TextFieldScreen()
}

@Composable
private fun PasswordFieldScreen() {
    var text by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Mật khẩu") },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Text(if (visible) "Ẩn" else "Hiện", modifier = Modifier.clickable { visible = !visible }, color = Color(0xFF1B78FF))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        shape = RoundedCornerShape(24.dp)
    )
}

@Preview
@Composable
private fun PasswordFieldScreenPreview() {
    PasswordFieldScreen()
}

@Composable
private fun RowLayoutScreen() {
    val colors = listOf(
        Color(0xFFBBD3FF), Color(0xFF9FC2FF), Color(0xFF6FA6FF),
        Color(0xFFBBD3FF), Color(0xFF9FC2FF), Color(0xFF6FA6FF),
        Color(0xFFBBD3FF), Color(0xFF9FC2FF), Color(0xFF6FA6FF),
    )
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color(0xFFF3F6FA)).padding(14.dp)
    ) {
        repeat(3) { r ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(3) { c ->
                    val idx = r * 3 + c
                    Box(
                        Modifier.weight(1f).height(68.dp).clip(RoundedCornerShape(14.dp)).background(colors[idx])
                    )
                }
            }
            if (r != 2) Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun RowLayoutScreenPreview() {
    RowLayoutScreen()
}

@Composable
private fun ColumnLayoutScreen() {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color(0xFFF3F6FA)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        fun green(alpha: Float) = Color(0xFF37C172).copy(alpha = alpha)
        Box(Modifier.fillMaxWidth().height(96.dp).clip(RoundedCornerShape(18.dp)).background(green(0.35f)))
        Box(Modifier.fillMaxWidth().height(96.dp).clip(RoundedCornerShape(18.dp)).background(green(0.85f)))
        Box(Modifier.fillMaxWidth().height(96.dp).clip(RoundedCornerShape(18.dp)).background(green(0.35f)))
    }
}

@Preview
@Composable
private fun ColumnLayoutScreenPreview() {
    ColumnLayoutScreen()
}
