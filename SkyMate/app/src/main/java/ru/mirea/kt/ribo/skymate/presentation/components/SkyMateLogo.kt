package ru.mirea.kt.ribo.skymate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mirea.kt.ribo.skymate.R

@Composable
fun SkyMateLogo(
    modifier: Modifier = Modifier,
    size: Dp = 88.dp
) {
    Image(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_skymate_logo),
        contentDescription = "Логотип SkyMate"
    )
}