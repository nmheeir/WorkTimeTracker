package com.example.worktimetracker.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.ProfileOption
import com.example.worktimetracker.ui.util.lOptionProfile

@Composable
fun OptionSection(
    modifier: Modifier = Modifier,
    onNavigateTo: (Screens) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(lOptionProfile.size - 1) {
            OptionSectionItem(
                profileOption = lOptionProfile[it],
                onNavigateTo = onNavigateTo
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun OptionSectionItem(
    modifier: Modifier = Modifier,
    profileOption: ProfileOption = lOptionProfile[0],
    onNavigateTo: (Screens) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(8.dp)
            .clickable {
                onNavigateTo(
                    profileOption.screens!!
                )
            }
    ) {
        Icon(
            painter = painterResource(id = profileOption.icon),
            contentDescription = profileOption.title,
            modifier = Modifier
                .clip(CircleShape)
                .background(AppTheme.colors.secondarySurface)
                .padding(8.dp)
        )
        Text(
            text = profileOption.title,
            style = Typography.labelLarge,
            modifier = Modifier.weight(1f),
            color = AppTheme.colors.onRegularSurface
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = AppTheme.colors.onRegularSurface
        )

    }
}