
package com.example.th3


import androidx.compose.material3.ExperimentalMaterial3Api
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

private object Route {
    const val ROOT = "root"
    const val LIST = "list"
    const val DETAIL = "detail"
}

@Composable
fun App() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Route.ROOT) {

        composable(Route.ROOT) {
            RootScreen(onGoList = { nav.navigate(Route.LIST) })
        }

        composable(Route.LIST) {
            ListScreen(
                onItemClick = { idx, text ->
                    val safe = java.net.URLEncoder.encode(text, "UTF-8")
                    nav.navigate("${Route.DETAIL}/$idx/$safe")
                },
                onBackToRoot = { nav.popBackStack(Route.ROOT, false) }
            )
        }

        composable(
            route = "${Route.DETAIL}/{id}/{text}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("text") { type = NavType.StringType }
            )
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: 0
            val textEncoded = backStack.arguments?.getString("text").orEmpty()
            val text = java.net.URLDecoder.decode(textEncoded, "UTF-8")
            DetailScreen(
                id = id,
                text = text,
                onBackToList = { nav.navigateUp() },
                onBackToRoot = { nav.popBackStack(Route.ROOT, false) }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(onGoList: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Root Screen") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Navigation Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text("Root → List (LazyColumn) → Detail")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onGoList) { Text("PUSH") }
        }
    }
}

@Composable
fun ListScreen(
    onItemClick: (Int, String) -> Unit,
    onBackToRoot: () -> Unit
) {
    val data = remember {
        List(20) { i -> "%02d | The only way to do great work is to love what you do.".format(i + 1) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn") },
                navigationIcon = {
                    IconButton(onClick = onBackToRoot) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Root")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(data) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(index, item) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(Icons.Filled.ArrowForward, contentDescription = null)
                }
                Divider(thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun DetailScreen(
    id: Int,
    text: String,
    onBackToList: () -> Unit,
    onBackToRoot: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Detail") }) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = onBackToList, modifier = Modifier.weight(1f)) {
                    Text("BACK TO LIST")
                }
                Button(onClick = onBackToRoot, modifier = Modifier.weight(1f)) {
                    Text("BACK TO ROOT")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "\"The only way to do great work\nis to love what you do.\"",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(16.dp))
            Text("Item: #${id + 1}")
            Spacer(Modifier.height(8.dp))
            Text(text)
        }
    }
}
