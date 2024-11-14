package com.ahmed_apps.run.presentation.run_overview.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.ahmed_apps.core.domian.location.Location
import com.ahmed_apps.core.domian.run.Run
import com.ahmed_apps.core.presentation.designsystem.CalendarIcon
import com.ahmed_apps.core.presentation.designsystem.RunOutlinedIcon
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.ahmed_apps.run.presentation.R
import com.ahmed_apps.run.presentation.run_overview.mappers.toRunUi
import com.ahmed_apps.run.presentation.run_overview.model.RunDataUi
import com.ahmed_apps.run.presentation.run_overview.model.RunUi
import java.time.ZonedDateTime
import kotlin.math.max
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * @author Ahmed Guedmioui
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RunListItem(
    modifier: Modifier = Modifier,
    runUi: RunUi,
    onDelete: () -> Unit
) {
    var showDorpDown by remember {
        mutableStateOf(false)
    }

    Box {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface)
                .combinedClickable(
                    onClick = { },
                    onLongClick = { showDorpDown = true }
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MapImage(imageUrl = runUi.mapPictureUrl)

            RunningTimeSection(
                duration = runUi.duration,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f)
            )

            RunningDateSection(dateTime = runUi.dateTime)

            DataGrid(
                runUi = runUi,
                modifier = Modifier.fillMaxWidth()
            )
        }
        DropdownMenu(
            expanded = showDorpDown,
            onDismissRequest = { showDorpDown = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.delete))
                },
                onClick = {
                    showDorpDown = false
                    onDelete()
                }
            )
        }
    }
}

@Composable
private fun MapImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.run_map),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(15.dp)),
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_couldn_t_load_image),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
private fun RunningTimeSection(
    modifier: Modifier = Modifier,
    duration: String,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary.copy(0.15f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = RunOutlinedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.total_running_time),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = duration,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun RunningDateSection(
    modifier: Modifier = Modifier,
    dateTime: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = CalendarIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = dateTime,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DataGrid(
    modifier: Modifier = Modifier,
    runUi: RunUi
) {

    val runDataUiList = listOf(
        RunDataUi(
            name = stringResource(R.string.distance),
            value = runUi.distance
        ),
        RunDataUi(
            name = stringResource(R.string.pace),
            value = runUi.pace
        ),
        RunDataUi(
            name = stringResource(R.string.avg_speed),
            value = runUi.avgSpeed
        ),
        RunDataUi(
            name = stringResource(R.string.max_speed),
            value = runUi.distance
        ),
        RunDataUi(
            name = stringResource(R.string.total_elevation),
            value = runUi.totalElevation
        ),
        RunDataUi(
            name = stringResource(R.string.avg_heart_rate),
            value = runUi.maxHeartRate
        ),
        RunDataUi(
            name = stringResource(R.string.max_heart_rate),
            value = runUi.maxHeartRate
        )
    )

    var maxWith by remember {
        mutableIntStateOf(0)
    }

    val maxWithDp = with(LocalDensity.current) { maxWith.toDp() }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        runDataUiList.forEach { runUi ->
            DataGridCell(
                runDataUi = runUi,
                modifier = Modifier
                    .defaultMinSize(minWidth = maxWithDp)
                    .onSizeChanged {
                        maxWith = max(maxWith, it.width)
                    }
            )
        }
    }

}

@Composable
private fun DataGridCell(
    modifier: Modifier = Modifier,
    runDataUi: RunDataUi,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = runDataUi.name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = runDataUi.value,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun RunListItemUiPreview() {
    RunningTrackerTheme {
        RunListItem(
            runUi = Run(
                id = "123",
                duration = 12.minutes + 23.seconds,
                dateTimeUtc = ZonedDateTime.now(),
                distanceMeters = 2345,
                location = Location(0.0, 0.0),
                maxSpeedKmh = 12.2454,
                totalElevationMeters = 124,
                mapPictureUrl = null,
                avgHeartRate = 120,
                maxHeartRate = 150
            ).toRunUi(),
            onDelete = {}
        )
    }
}





















