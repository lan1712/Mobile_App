package com.example.tuan_4

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun UthSmartTasksApp() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val startDestination = if (auth.currentUser != null) "profile" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController = navController) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("forgot_password_flow") { ForgotPasswordFlow(mainNavController = navController) }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val oneTapClient = remember { Identity.getSignInClient(context) }
    val signInRequest = remember {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Sửa lỗi: Lấy client ID từ resource, không hardcode
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false).build()
            ).setAutoSelectEnabled(true).build()
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credential.googleIdToken
                if (googleIdToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    coroutineScope.launch {
                        Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                                navController.navigate("profile") { popUpTo("login") { inclusive = true } }
                            } else {
                                Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } catch (e: ApiException) {
                Log.e("LoginScreen", "Login failed: ${e.localizedMessage}")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.uth), contentDescription = "UTH Logo", modifier = Modifier.size(150.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(32.dp))
        Text("Welcome", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        activityResultLauncher.launch(intentSenderRequest)
                    } catch (e: Exception) {
                        Log.e("LoginScreen", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("LoginScreen", "Begin sign in failed: ${e.localizedMessage}")
                    Toast.makeText(context, "Login with Google failed. Check SHA-1 config in Firebase.", Toast.LENGTH_LONG).show()
                }
        }) {
            Text("SIGN IN WITH GOOGLE")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("forgot_password_flow") }) {
            Text("Forgot Password?")
        }
    }
}



@Composable
fun ProfileScreen(navController: NavController) {
    val auth = Firebase.auth
    val user = auth.currentUser

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        if (user != null) {
            Text("Name: ${user.displayName}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Email: ${user.email}")
        } else {
            Text("No user logged in.")
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            auth.signOut()
            navController.navigate("login") { popUpTo("profile") { inclusive = true } }
        }) {
            Text("Sign Out")
        }
    }
}

@Composable
fun ForgotPasswordFlow(mainNavController: NavController) {
    App_Bai1(mainNavController = mainNavController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App_Bai1(mainNavController: NavController) {
    val nav = rememberNavController()
    var email by rememberSaveable { mutableStateOf("") }
    var verifyCode by rememberSaveable { mutableStateOf(0) }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Reset Password", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = { mainNavController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) { inner ->
        NavHost(
            navController = nav,
            startDestination = "forgot",
            modifier = Modifier.padding(inner)
        ) {
            composable("forgot") {
                ForgotScreen_Bai1(
                    email = email,
                    onEmailChange = { email = it },
                    onNext = { nav.navigate("verify") }
                )
            }
            composable("verify") {
                VerifyScreen_Bai1(
                    code = verifyCode,
                    onCodeChange = { verifyCode = it },
                    onBack = { nav.popBackStack() },
                    onNext = { if (verifyCode.toString().length == 6) nav.navigate("reset") }
                )
            }
            composable("reset") {
                ResetPasswordScreen_Bai1(
                    onBack = { nav.popBackStack() },
                    onSubmit = { newPass ->
                        password = newPass
                        nav.navigate("confirm")
                    }
                )
            }
            composable("confirm") {
                ConfirmScreen_Bai1(
                    email = email,
                    code = verifyCode,
                    password = password,
                    onFinish = { mainNavController.popBackStack() },
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun ForgotScreen_Bai1(email: String, onEmailChange: (String) -> Unit, onNext: () -> Unit) {
    val context = LocalContext.current
    val auth = Firebase.auth
    val isValidEmail = remember(email) { android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() }

    ScreenScaffold_Bai1(
        title = "Forget Password?",
        subtitle = "Enter your Email, we will send you a verification code."
    ) {
        OutlinedTextField(
            value = email, onValueChange = onEmailChange, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Your Email") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = email.isNotEmpty() && !isValidEmail,
            supportingText = { if (email.isNotEmpty() && !isValidEmail) Text("Invalid email format", color = MaterialTheme.colorScheme.error) }
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Password reset email sent.", Toast.LENGTH_SHORT).show()
                            onNext()
                        } else {
                            Toast.makeText(context, "Failed to send email: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            },
            enabled = isValidEmail, modifier = Modifier.fillMaxWidth().height(48.dp)
        ) { Text("Next") }
    }
}

@Composable
fun VerifyScreen_Bai1(code: Int, onCodeChange: (Int) -> Unit, onBack: () -> Unit, onNext: () -> Unit) {
    val codeString = if (code == 0) "" else code.toString()
    ScreenScaffold_Bai1(
        title = "Verify Code",
        subtitle = "Enter the 6-digit code we just sent to your registered Email.",
        onBack = onBack
    ) {
        OutlinedTextField(
            value = codeString,
            onValueChange = {
                val onlyDigits = it.filter { ch -> ch.isDigit() }.take(6)
                onCodeChange(onlyDigits.toIntOrNull() ?: 0)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter 6 digits") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = codeString.isNotEmpty() && codeString.length < 6,
            supportingText = { if (codeString.isNotEmpty() && codeString.length < 6) Text("Verification code must be 6 digits", color = MaterialTheme.colorScheme.error) }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNext, enabled = codeString.length == 6, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text("Next") }
    }
}

@Composable
fun ResetPasswordScreen_Bai1(onBack: () -> Unit, onSubmit: (String) -> Unit) {
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    val ok = pass.length >= 6 && pass == confirm

    ScreenScaffold_Bai1(
        title = "Create new password",
        subtitle = "Your new password must be different from the previous one.",
        onBack = onBack
    ) {
        OutlinedTextField(value = pass, onValueChange = { pass = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Password") }, singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = confirm, onValueChange = { confirm = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Confirm Password") }, singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))
        Button(onClick = { if (ok) onSubmit(pass) }, enabled = ok, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text("Next") }
    }
}

@Composable
fun ConfirmScreen_Bai1(email: String, code: Int, password: String, onFinish: () -> Unit, onBack: () -> Unit) {
    ScreenScaffold_Bai1(
        title = "Confirm",
        subtitle = "Password has been reset successfully!",
        onBack = onBack
    ) {
        ReadOnlyField_Bai1("Email", email.ifBlank { "—" })
        Spacer(Modifier.height(8.dp))
        ReadOnlyField_Bai1("Code", if (code == 0) "—" else code.toString())
        Spacer(Modifier.height(8.dp))
        ReadOnlyField_Bai1("New Password", "•".repeat(password.length.coerceAtLeast(6)))
        Spacer(Modifier.height(16.dp))
        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text("Finish") }
    }
}

@Composable
private fun ScreenScaffold_Bai1(title: String, subtitle: String, onBack: (() -> Unit)? = null, content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, content = content)
    }
}

@Composable
private fun ReadOnlyField_Bai1(label: String, value: String) {
    OutlinedTextField(value = value, onValueChange = {}, modifier = Modifier.fillMaxWidth(), readOnly = true, label = { Text(label) })
}
