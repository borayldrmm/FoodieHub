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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.theme.appNameTitle
import com.borayildirim.foodiehub.presentation.theme.homeSubtitle

/**
 * Home screen header component displaying app branding and user profile
 *
 * Shows the FoodieHub branding with subtitle and a circular profile image
 * that navigates to the profile screen when clicked. The profile image
 * automatically updates when the user changes their profile picture.
 *
 * @param profileImageUrl Optional URL/path to user's profile picture.
 *                        If null, displays default profile icon
 * @param onProfileClick Callback invoked when profile image is clicked,
 *                       typically used for navigation to profile screen
 *
 * @see com.borayildirim.foodiehub.presentation.screens.HomeScreen
 * @see com.borayildirim.foodiehub.presentation.screens.ProfileScreen
 */
@Composable
fun HomeHeader(
    profileImageUrl: String? = null,
    onProfileClick: () -> Unit = { }
) {
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
            painter = if (profileImageUrl != null) {
                rememberAsyncImagePainter(profileImageUrl)
            } else {
                painterResource(R.drawable.profile_icon)
            },
            contentDescription = stringResource(R.string.profile_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }
        )
    }
}