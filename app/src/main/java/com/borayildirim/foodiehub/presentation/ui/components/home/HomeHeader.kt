package com.borayildirim.foodiehub.presentation.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme
import com.borayildirim.foodiehub.presentation.theme.appNameTitle
import com.borayildirim.foodiehub.presentation.theme.homeSubtitle


@Composable
fun HomeHeader(onProfileClick: () -> Unit = { }) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.appNameTitle,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.home_subtitle_text),
                style = MaterialTheme.typography.homeSubtitle
            )
            Spacer(Modifier.padding(vertical = 20.dp))
        }

        Image(
            painter = painterResource(R.drawable.profile_icon),
            contentDescription = stringResource(R.string.profile_content_description),
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }
        )
    }
}