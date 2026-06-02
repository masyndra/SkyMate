package ru.mirea.kt.ribo.skymate.presentation.mainweather

import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.kt.ribo.skymate.presentation.components.AppScreenHeader
import ru.mirea.kt.ribo.skymate.presentation.components.WeatherInfoCard

@Composable
fun MainWeatherScreen(
    onOpenDistrictList: () -> Unit,
    onLogoutSuccess: () -> Unit,
    viewModel: MainWeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineGranted || coarseGranted) {
            viewModel.detectDistrictByLocation()
        } else {
            viewModel.onLocationPermissionDenied()
        }
    }
    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        /*
         * Если пользователь отказал, приложение всё равно работает,
         * просто уведомления не будут показываться.
         */
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
    MainWeatherContent(
        uiState = uiState,
        onOpenDistrictList = onOpenDistrictList,
        onRefreshClick = viewModel::refreshWeather,
        onDetectLocationClick = {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        },
        onLogoutClick = {
            viewModel.logout(
                onSuccess = onLogoutSuccess
            )
        }
    )
}

@Composable
private fun MainWeatherContent(
    uiState: MainWeatherUiState,
    onOpenDistrictList: () -> Unit,
    onRefreshClick: () -> Unit,
    onDetectLocationClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppScreenHeader(
                    title = "SkyMate",
                    subtitle = "Погодный помощник по районам Москвы"
                )
            }

            IconButton(
                onClick = onLogoutClick
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Выйти"
                )
            }
        }

        when {
            uiState.isLoading -> {
                LoadingBlock()
            }

            uiState.district == null -> {
                NoDistrictBlock(
                    uiState = uiState,
                    onOpenDistrictList = onOpenDistrictList,
                    onDetectLocationClick = onDetectLocationClick
                )
            }

            else -> {
                SelectedDistrictBlock(
                    uiState = uiState,
                    onOpenDistrictList = onOpenDistrictList,
                    onRefreshClick = onRefreshClick,
                    onDetectLocationClick = onDetectLocationClick
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun LoadingBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "Загрузка погоды...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun NoDistrictBlock(
    uiState: MainWeatherUiState,
    onOpenDistrictList: () -> Unit,
    onDetectLocationClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
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
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Основной район не выбран",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = uiState.errorMessage ?: "Выберите район вручную или определите его по GPS.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = !uiState.isDetectingLocation,
                onClick = onOpenDistrictList
            ) {
                Text(text = "Выбрать район")
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = !uiState.isDetectingLocation,
                onClick = onDetectLocationClick
            ) {
                if (uiState.isDetectingLocation) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Определить по GPS"
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectedDistrictBlock(
    uiState: MainWeatherUiState,
    onOpenDistrictList: () -> Unit,
    onRefreshClick: () -> Unit,
    onDetectLocationClick: () -> Unit
) {
    val district = uiState.district ?: return

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
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
                text = "Основной район",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = district.shortName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = district.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = district.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isDetectingLocation,
                    onClick = onOpenDistrictList
                ) {
                    Text(text = "Сменить")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isRefreshing && !uiState.isDetectingLocation,
                    onClick = onRefreshClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Обновить"
                    )
                }
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = !uiState.isDetectingLocation,
                onClick = onDetectLocationClick
            ) {
                if (uiState.isDetectingLocation) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Определить район по GPS"
                    )
                }
            }
        }
    }

    if (uiState.isRefreshing) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp
            )

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "Обновление погоды...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (uiState.weather != null) {
        WeatherInfoCard(
            modifier = Modifier.padding(top = 16.dp),
            weather = uiState.weather,
            advice = uiState.weatherAdvice,
            conditionText = uiState.weatherConditionText
        )
    }

    if (uiState.errorMessage != null) {
        WeatherErrorCard(
            message = uiState.errorMessage,
            onRetryClick = onRefreshClick
        )
    }
}
@Composable
private fun WeatherErrorCard(
    message: String,
    onRetryClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
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
                text = "Не удалось загрузить погоду",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = onRetryClick
            ) {
                Text(text = "Повторить")
            }
        }
    }
}