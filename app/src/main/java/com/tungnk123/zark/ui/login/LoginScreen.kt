package com.tungnk123.zark.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
    ) { contentPaddings ->
        Column(
            modifier = Modifier.padding(contentPaddings)
        ) {
            Text(text = "Login screen")
            Button(onClick = {
                navController.navigate(NavigationBarMetadataItem.Signin.route.route)
            }) {
                Text("Sign in")
            }
        }
    }
}