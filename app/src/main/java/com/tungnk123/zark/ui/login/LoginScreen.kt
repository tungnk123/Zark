package com.tungnk123.zark.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tungnk123.zark.R

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.bg_login),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(), containerColor = Color.Transparent
        ) { contentPaddings ->
            Column(
                modifier = Modifier
                    .padding(contentPaddings)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(71.dp))
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    modifier = Modifier.size(92.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Chào mừng bạn trở lại",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "Đăng nhập vào tài khoản của bạn để tiếp tục cuộc trò chuyện",
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

