package com.tungnk123.zark.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    currentTab: NavigationBarMetadataItem,
    tabItemsList: List<NavigationBarMetadataItem>,
    onTabSelected: (NavigationBarMetadataItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.width(2.dp))

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
                        Image(
                            painter = painterResource(if (it) tab.selectedIconRes else tab.unselectedIconRes),
                            contentDescription = tab.navigationRoute.route
                        )
                    }
                },
                label = {
                    Text(
                        text = tab.navigationRoute.name,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )
                },
                onClick = {
                    onTabSelected(tab)
                }
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
    }
}