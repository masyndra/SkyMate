package ru.mirea.kt.ribo.skymate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mirea.kt.ribo.skymate.domain.model.Weather

@Composable
fun WeatherInfoCard(
    weather: Weather,
    advice: String,
    conditionText: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Текущая погода",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            WeatherRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Cloud,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                title = "Состояние",
                value = conditionText.ifBlank {
                    "Нет данных"
                }
            )

            WeatherRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Thermostat,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                title = "Температура",
                value = "%.1f °C".format(weather.temperature)
            )

            WeatherRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Air,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                title = "Скорость ветра",
                value = "%.1f км/ч".format(weather.windSpeed)
            )

            WeatherRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Umbrella,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                title = "Осадки",
                value = "%.1f мм".format(weather.precipitation)
            )

            WeatherRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                title = "Влажность",
                value = weather.humidity?.let { "$it %" } ?: "нет данных"
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Совет",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = advice,
                style = MaterialTheme.typography.bodyMedium
            )

            if (weather.time.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "Время обновления: ${weather.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun WeatherRow(
    icon: @Composable () -> Unit,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}