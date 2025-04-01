package com.tungnk123.zark.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.common.CustomTextField
import com.tungnk123.zark.ui.common.PrimaryButton
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem
import com.tungnk123.zark.ui.theme.c_4A86F7
import com.tungnk123.zark.ui.theme.c_6A7185

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { contentPaddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                    text = stringResource(R.string.msg_login_title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.Black,
                        fontSize = 20.sp
                    ),
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = stringResource(R.string.msg_login_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = c_6A7185,
                        fontSize = 13.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))

                CustomTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    labelResId = R.string.msg_phone,
                    leadIconResId = R.drawable.ic_phone,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    labelResId = R.string.msg_password,
                    leadIconResId = R.drawable.ic_lock,
                    isPasswordField = true,
                    trailingIconResId = R.drawable.ic_eye,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = stringResource(R.string.msg_forget_password),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = c_4A86F7,
                        fontSize = 13.sp,
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                PrimaryButton(
                    textResId = R.string.msg_login,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.msg_no_account),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = c_6A7185
                        )
                    )
                    TextButton(onClick = {
                        navController.navigate(NavigationBarMetadataItem.Signin.navigationRoute.route)
                    }) {
                        Text(
                            text = stringResource(R.string.msg_signin_now),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = c_4A86F7
                            )
                        )
                    }
                }

            }
        }
    }
}