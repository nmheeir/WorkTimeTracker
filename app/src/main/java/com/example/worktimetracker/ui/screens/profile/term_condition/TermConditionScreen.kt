package com.example.worktimetracker.ui.screens.profile.term_condition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun TermConditionScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.term_condition,
                onBack = onBack
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = 24.dp,
                end = 24.dp
            )
        ) {
            Text(
                text = "Last Update: 07/15/2024",
                style = Typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.term_condition_notify),
                style = Typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.condition_of_uses),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.condition_des),
                style = Typography.bodyMedium
            )
        }
    }
}

@Composable
fun PrivacyPolicyScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.term_condition,
                onBack = onBack
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = 24.dp,
                end = 24.dp
            )
        ) {
            Text(
                text = "Last Update: 07/15/2024",
                style = Typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.policy_notify),
                style = Typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.privacy_policy),
                style = Typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.policy_des),
                style = Typography.bodyMedium
            )
        }
    }
}