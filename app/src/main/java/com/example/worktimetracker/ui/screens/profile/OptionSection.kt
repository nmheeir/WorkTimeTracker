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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.ProfileOption
import com.example.worktimetracker.ui.util.lOptionProfile

@Preview(showBackground = true)
@Composable
fun OptionSection(modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lOptionProfile.size - 1) {
            OptionSectionItem(profileOption = lOptionProfile[it])
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun OptionSectionItem(
    modifier: Modifier = Modifier,
    profileOption: ProfileOption = lOptionProfile[0],
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.white))
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = profileOption.icon),
            contentDescription = profileOption.title,
            modifier = Modifier
                .clip(CircleShape)
                .background(color = colorResource(id = R.color.light_gray))
                .padding(8.dp)
        )
        Text(
            text = profileOption.title,
            style = Typography.labelLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
        )

    }
}