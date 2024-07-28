package com.example.worktimetracker.ui.screens.worktime.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.ui.util.exampleWorkTime
import com.example.worktimetracker.ui.util.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.compose.common.rememberLegendItem
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkTimeChart(modifier: Modifier = Modifier) {
    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            modelProducer.runTransaction {
                columnSeries {

                }
            }
        }
    }

    ExampleChart(modifier, modelProducer)
}

@Composable
fun ExampleChart(modifier: Modifier = Modifier, modelProducer: CartesianChartModelProducer) {
    val marker = rememberMarker()
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        color = chartColors[0],
                        thickness = COLUMN_THICKNESS.dp
                    ),
                    rememberLineComponent(
                        color = chartColors[1],
                        thickness = COLUMN_THICKNESS.dp
                    ),
                    rememberLineComponent(
                        color = chartColors[2],
                        thickness = COLUMN_THICKNESS.dp
                    )
                ),
                mergeMode = { ColumnCartesianLayer.MergeMode.Stacked }
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                guideline = null,
                valueFormatter = bottomAxisValueFormatter
            ),
            marker = marker,
            legend = rememberLegend(),
            decorations = listOf(
                rememberHorizontalLine(
                    y = {
                        12.0
                    },
                    line = rememberLineComponent(
                        thickness = 2.dp
                    ),
                    labelComponent =
                    rememberTextComponent(
                        margins = Dimensions.of(1.dp),
                        padding =
                        Dimensions.of(
                            1.dp,
                            1.dp,
                        ),
                        background = rememberShapeComponent(chartColors[0], Shape.Pill),
                    ),
                )
            )
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = true)
    )

}

@Composable
private fun rememberLegend() =
    rememberHorizontalLegend<CartesianMeasureContext, CartesianDrawContext>(
        items = chartColors.mapIndexed { index, chartColor ->
            rememberLegendItem(
                icon = rememberShapeComponent(chartColor, Shape.Pill),
                labelComponent = rememberTextComponent(vicoTheme.textColor),
                label = "Shift + $index",
            )
        },
        iconSize = 8.dp,
        iconPadding = 8.dp,
        spacing = 4.dp,
        padding = Dimensions.of(top = 8.dp),
    )

private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
    daysOfWeek[x.toInt() % daysOfWeek.size]
}
private const val COLUMN_THICKNESS = 16
private val chartColors = listOf(
    Color(0xffb983ff),
    Color(0xff91b1fd),
    Color(0xff8fdaff)
)