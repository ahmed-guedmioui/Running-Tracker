package com.ahmed_apps.analytics.presentation.components

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun DistancesGraph(
    modifier: Modifier = Modifier,
    distances: List<Double> = listOf(0.4, 0.0, 0.8, 0.3, 0.7, 0.8, 0.3, 0.7, 0.7, 0.8),
    paddingSpace: Dp = 6.dp,
) {

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
        }
    }

    val maxValue = distances.maxOrNull()?.roundToInt() ?: 0
    val verticalStep = maxValue / 4.0

    val yValues = List(5) { index -> index * verticalStep }
    val xValues = List(distances.size) { index -> index }

    val pointRadius = 17f
    val verticalSpacing = 100f
    val horizontalSpacing = 30f

    val coordinates = mutableListOf<PointF>()
    val primary = MaterialTheme.colorScheme.primary

    var clickedIndex by remember {
        mutableStateOf<Int?>(null)
    }

    var clickedPoint by remember {
        mutableStateOf<PointF?>(null)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val touchedPoint = coordinates.find { point ->
                        val dx = offset.x - point.x
                        val dy = offset.y - point.y
                        sqrt(dx * dx + dy * dy) <= pointRadius
                    }
                    if (touchedPoint != null) {
                        clickedIndex = coordinates.indexOf(touchedPoint)
                        clickedPoint = touchedPoint
                    } else {
                        clickedIndex = null
                    }
                }
            }
    ) {

        val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
        val yAxisSpace = size.height / yValues.size

        guideLines(
            yAxisSpace = yAxisSpace,
            paddingSpace = paddingSpace,
            yValues = yValues,
            verticalSpacing = verticalSpacing,
            horizontalSpacing = horizontalSpacing
        )

        xAxisTexts(
            xAxisSpace = xAxisSpace,
            textPaint = textPaint,
            xValues = xValues,
            horizontalSpacing = horizontalSpacing
        )

        yAxisTexts(
            paddingSpace = paddingSpace,
            yAxisSpace = yAxisSpace,
            yValues = yValues,
            textPaint = textPaint,
            spacing = verticalSpacing
        )

        points(
            xAxisSpace = xAxisSpace,
            yAxisSpace = yAxisSpace,
            xValues = xValues,
            distances = distances,
            verticalStep = verticalStep,
            verticalSpacing = verticalSpacing,
            pointRadius = pointRadius,
            horizontalSpacing = horizontalSpacing,
            coordinates = coordinates
        )

        val path = path(
            xAxisSpace = xAxisSpace,
            yAxisSpace = yAxisSpace,
            xValues = xValues,
            distances = distances,
            verticalStep = verticalStep,
            spacing = verticalSpacing,
            horizontalSpacing = horizontalSpacing,
            color = primary
        )

        fillColorUnderPath(
            path = path,
            yAxisSpace = yAxisSpace,
            coordinates = coordinates
        )


        clickedIndex?.let { index ->
            clickedPoint?.let { clickedPoint ->
                clickedPointText(
                    distances = distances,
                    clickedPoint = clickedPoint,
                    index = index,
                    textPaint = textPaint,
                    verticalSpacing = verticalSpacing,
                )
            }
        }
    }

}

private fun DrawScope.clickedPointText(
    distances: List<Double>,
    clickedPoint: PointF,
    index: Int,
    textPaint: Paint,
    verticalSpacing: Float
) {

    val text = "${distances[index]} km"

    val xPosition = when (index) {
        0 -> -60f
        distances.size - 1 -> 60f
        else -> 0f
    }

    val textBounds = android.graphics.Rect()
    textPaint.getTextBounds(text, 0, text.length, textBounds)
    val textWidth = textBounds.width().toFloat()
    val textHeight = textBounds.height().toFloat()

    val padding = 30f
    val marginFromPoint = 45

    val rectWidth = textWidth + padding * 2
    val rectHeight = textHeight + padding * 2

    drawRoundRect(
        topLeft = Offset(
            clickedPoint.x - rectWidth / 2 - xPosition,
            clickedPoint.y - rectHeight - marginFromPoint
        ),
        size = Size(rectWidth, rectHeight),
        color = Color.White,
        cornerRadius = CornerRadius(16f, 16f)
    )

    drawContext.canvas.nativeCanvas.drawText(
        text,
        clickedPoint.x - xPosition,
        clickedPoint.y - (rectHeight / 2) - marginFromPoint + (textHeight / 2),
        textPaint.apply {
            textSize = 16.sp.toPx()
            color = Color.Black.toArgb()
        }
    )

    val verticalLine = Path().apply {
        reset()
        moveTo(
            clickedPoint.x,
            clickedPoint.y - rectHeight - marginFromPoint
        )
        lineTo(
            clickedPoint.x,
            size.height - verticalSpacing
        )
    }

    drawPath(
        verticalLine,
        color = Color.White.copy(0.6f),
        style = Stroke(
            width = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f)),
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.yAxisTexts(
    paddingSpace: Dp,
    yAxisSpace: Float,
    yValues: List<Double>,
    textPaint: Paint,
    spacing: Float
) {
    for (i in yValues.indices) {
        drawContext.canvas.nativeCanvas.drawText(
            "${yValues[i]}",
            paddingSpace.toPx(),
            size.height - yAxisSpace * (i) - spacing,
            textPaint.apply {
                textSize = 13.sp.toPx()
                color = Color.White.toArgb()
            },
        )
    }
}

private fun DrawScope.xAxisTexts(
    xAxisSpace: Float,
    textPaint: Paint,
    xValues: List<Int>,
    horizontalSpacing: Float
) {
    for (i in xValues.indices) {
        drawContext.canvas.nativeCanvas.drawText(
            "${xValues[i]}",
            xAxisSpace * (i + 1) - horizontalSpacing,
            size.height,
            textPaint.apply {
                textSize = 13.sp.toPx()
                color = Color.White.toArgb()
            }
        )
    }
}

private fun DrawScope.guideLines(
    yAxisSpace: Float,
    paddingSpace: Dp,
    yValues: List<Double>,
    verticalSpacing: Float,
    horizontalSpacing: Float
) {

    for (i in yValues.indices) {
        val x = size.width - (horizontalSpacing + 50)
        val y = size.height - yAxisSpace * (i) - verticalSpacing
        val horizontalLine = Path().apply {
            reset()
            moveTo(
                paddingSpace.toPx() + horizontalSpacing + 50,
                size.height - yAxisSpace * (i) - verticalSpacing
            )
            lineTo(x, y)
        }

        drawPath(
            horizontalLine,
            color = Color.White.copy(0.5f),
            style = Stroke(
                width = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f)),
                cap = StrokeCap.Round
            )
        )
    }
}

private fun DrawScope.points(
    xAxisSpace: Float,
    yAxisSpace: Float,
    xValues: List<Int>,
    distances: List<Double>,
    verticalStep: Double,
    verticalSpacing: Float,
    pointRadius: Float,
    horizontalSpacing: Float,
    coordinates: MutableList<PointF>,
) {
    for (i in distances.indices) {
        val x1 = xAxisSpace * xValues[i] + xAxisSpace - horizontalSpacing
        val y1 =
            size.height - (yAxisSpace * (distances[i].toFloat() / verticalStep.toFloat())) - verticalSpacing
        coordinates.add(PointF(x1, y1))
        drawCircle(
            color = Color.White,
            radius = pointRadius,
            center = Offset(x1, y1)
        )
    }
}

private fun DrawScope.path(
    xAxisSpace: Float,
    yAxisSpace: Float,
    xValues: List<Int>,
    distances: List<Double>,
    verticalStep: Double,
    spacing: Float,
    horizontalSpacing: Float,
    coordinates: MutableList<PointF> = mutableListOf(),
    color: Color,
): Path {
    val path = Path().apply {
        reset()
        for (i in distances.indices) {
            val x1 = xAxisSpace * xValues[i] + xAxisSpace - horizontalSpacing
            val y1 =
                size.height - (yAxisSpace * (distances[i].toFloat() / verticalStep.toFloat())) - spacing
            coordinates.add(PointF(x1, y1))
        }

        moveTo(coordinates.first().x, coordinates.first().y)
        for (i in 0 until coordinates.size - 1) {
            val startX = coordinates[i].x
            val startY = coordinates[i].y
            val endX = coordinates[i + 1].x
            val endY = coordinates[i + 1].y
            val controlPoint1X = (startX + endX) / 2
            val controlPoint2X = (startX + endX) / 2
            cubicTo(controlPoint1X, startY, controlPoint2X, endY, endX, endY)
        }
    }

    drawPath(
        path,
        color = color,
        style = Stroke(
            width = 5f,
            cap = StrokeCap.Round
        )
    )

    return path
}

private fun DrawScope.fillColorUnderPath(
    path: Path,
    yAxisSpace: Float,
    coordinates: MutableList<PointF>
) {
    val fillPath = android.graphics.Path(path.asAndroidPath())
        .asComposePath()
        .apply {
            lineTo(coordinates.last().x, size.height)
            lineTo(coordinates.first().x, size.height)
            close()
        }
    drawPath(
        fillPath,
        brush = Brush.verticalGradient(
            listOf(
                Color.Green,
                Color.Transparent,
            ),
            endY = size.height - yAxisSpace
        ),
    )
}


@Preview
@Composable
private fun DistanceGraphPreview() {
    RunningTrackerTheme {
        Box(
            modifier = Modifier
                .height(400.dp)
        ) {
            DistancesGraph(
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 32.dp, bottom = 16.dp)
                    .align(TopCenter)
            )
        }
    }
}














