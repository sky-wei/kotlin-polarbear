package com.sky.account.manager.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021-11-01.
 */
@Composable
fun BearBigTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun BearSortTitle(
    title: String
) {
    Text(
        text = title,
        color = GrayText,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.absolutePadding(left = 28.dp)
    )
}

@Composable
fun BearTopBar(
    backText: String,
    backIcon: Painter,
    title: String,
    onBack: () -> Unit
) {
    BearTopBar(
        backText = backText,
        backIcon = backIcon,
        title = title,
        onBack = onBack
    ) {

    }
}

@Composable
fun BearTopBar(
    backText: String,
    backIcon: Painter,
    title: String,
    onBack: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onBack() }
                .absolutePadding(top = 6.dp, bottom = 6.dp, right = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = backIcon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(backText)
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
fun BearSubTitle(
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
    }
}