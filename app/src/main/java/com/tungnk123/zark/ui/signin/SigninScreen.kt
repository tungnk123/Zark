package com.tungnk123.zark.ui.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem

@Composable
fun SigninScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    signinViewModel: SigninViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
    ) { contentPaddings ->
        Column(
            modifier = Modifier.padding(contentPaddings)
        ) {
            Text(text = "Signin screen")
            Button(onClick = {
                navController.navigate(NavigationBarMetadataItem.Login.route.route)
            }) {
                Text("Login in")
            }
        }
    }
}