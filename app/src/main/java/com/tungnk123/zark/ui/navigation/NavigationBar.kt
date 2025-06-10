package com.tungnk123.zark.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.theme.c_4A86F7
import com.tungnk123.zark.ui.theme.c_848484

@Composable
fun BottomNavigationBar(
    currentTab: NavigationBarMetadataItem,
    tabItemsList: List<NavigationBarMetadataItem>,
    onTabSelected: (NavigationBarMetadataItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clipToBounds()) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = c_848484
        )

        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
        ) {
            tabItemsList.forEach { tab ->
                val isSelected = currentTab.navigationRoute == tab.navigationRoute

                NavigationBarItem(
                    selected = isSelected,
                    alwaysShowLabel = true,
                    icon = {
                        Crossfade(
                            label = "bottom-bar-${tab.navigationRoute}",
                            targetState = isSelected
                        ) {
                            val iconRes = if (it) {
                                tab.selectedIconRes ?: R.drawable.ic_chat_selected
                            }
                            else {
                                tab.unselectedIconRes ?: R.drawable.ic_chat
                            }
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = tab.navigationRoute.route,
                                modifier = Modifier
                                    .width(32.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = tab.navigationRoute.name,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp
                            ),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            modifier = Modifier
                                .padding(top = 0.dp)
                        )
                    },
                    onClick = { onTabSelected(tab) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedTextColor = c_4A86F7,
                        selectedIconColor = c_4A86F7,
                        unselectedTextColor = c_848484,
                        unselectedIconColor = c_848484
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            horizontal = 0.dp,
                            vertical = 2.dp
                        )
                )
            }
        }
    }
}