package com.tungnk123.zark.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.tungnk123.zark.data.dto.user.UserConnection
import com.tungnk123.zark.data.dto.user.UserDto
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem
import com.tungnk123.zark.utils.extensions.navigateToDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by profileViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                windowInsets = WindowInsets(0),
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { contentPaddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddings)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { profileViewModel.loadUserProfile() }) {
                            Text("Retry")
                        }
                    }
                }

                uiState.user != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }

                        item {
                            ProfileHeader(user = uiState.user!!)
                        }

                        item {
                            ProfileInfoCard(user = uiState.user!!)
                        }

                        item {
                            ConnectionsCard(connections = uiState.user!!.connections)
                        }

                        item {
                            DevicesCard(devices = uiState.user!!.devices)
                        }

                        item {
                            Button(
                                onClick = {
                                    navController.navigateToDestination(
                                        destination = NavigationBarMetadataItem.Login,
                                        clearBackStack = true
                                    )
                                    profileViewModel.logout()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Text(text = "Logout")
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: UserDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user.avatarUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(user.avatarUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = user.displayName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = user.email,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (user.isValidAccount) Icons.Default.Person else Icons.Default.Person,
                    contentDescription = null,
                    tint = if (user.isValidAccount) Color.Green else Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (user.isValidAccount) "Verified Account" else "Unverified Account",
                    fontSize = 14.sp,
                    color = if (user.isValidAccount) Color.Green else Color.Red
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoCard(user: UserDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Account Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                icon = Icons.Default.Person,
                label = "User ID",
                value = user.id.toString()
            )

            InfoRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = user.email
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ConnectionsCard(connections: List<UserConnection>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Connections (${connections.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (connections.isEmpty()) {
                Text(
                    text = "No connections yet",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            else {
                connections.forEach { connection ->
                    ConnectionItem(connection = connection)
                }
            }
        }
    }
}

@Composable
private fun ConnectionItem(connection: UserConnection) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = connection.user ?: "Unknown User",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Connection ID: ${connection.connectionId}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DevicesCard(devices: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Connected Devices (${devices.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (devices.isEmpty()) {
                Text(
                    text = "No devices connected",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            else {
                devices.forEach { device ->
                    DeviceItem(device = device)
                }
            }
        }
    }
}

@Composable
private fun DeviceItem(device: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = Color.Green,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = device,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}