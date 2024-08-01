package com.example.worktimetracker.ui.screens.worktime.component

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.ShiftType
import com.example.worktimetracker.helper.Helper.Companion.convertLongToWorkTime
import com.example.worktimetracker.helper.Helper.Companion.formatMillisToDate
import com.example.worktimetracker.ui.screens.worktime.WorkTimeUiState
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

@Composable
fun WorkTimeChart(
    modifier: Modifier = Modifier,
    state: WorkTimeUiState = WorkTimeUiState()
) {
    val modelProducer = remember {
        CartesianChartModelProducer()
    }
    var chartData by remember { mutableStateOf(emptyList<DayWorkTime>()) }

    val days: List<String> by remember {
        derivedStateOf {
            chartData
                .map {
                    it.date.formatMillisToDate()
                }
                .distinct()
        }
    }

    val bottomAxisValue = CartesianValueFormatter { x, _, _ ->
        days[x.toInt()]
    }

    val normalWork by remember {
        derivedStateOf {
            chartData.filter { it.type == ShiftType.Normal.ordinal }
        }
    }
    val overtimeWork by remember {
        derivedStateOf {
            chartData.filter { it.type == ShiftType.Overtime.ordinal }
        }
    }
    val nightWork by remember {
        derivedStateOf {
            chartData.filter { it.type == ShiftType.NightShift.ordinal }
        }
    }

    LaunchedEffect(state.chartData) {
        chartData = state.chartData

        Log.d("worktimeChart", chartData.toString())
        Log.d("worktimeChart", "normalWork: $normalWork")
        Log.d("worktimeChart", "overtimeWork: $overtimeWork")
        Log.d("worktimeChart", "nightWork: $nightWork")
        Log.d("worktimeChart", "days: $days")

        if (chartData.isNotEmpty()) {
            withContext(Dispatchers.Default) {
                modelProducer.runTransaction {
                    columnSeries {
                        series(
                            List(days.size) { index ->
                                val dayDate = days[index]
                                val normalWorkItem = normalWork.find { it.date.formatMillisToDate() == dayDate }
                                normalWorkItem?.workTime?.convertLongToWorkTime() ?: 0
                            }
                        )
                        series(
                            List(days.size) { index ->
                                val dayDate = days[index]
                                val overtimeWorkItem = overtimeWork.find { it.date.formatMillisToDate() == dayDate }
                                overtimeWorkItem?.workTime?.convertLongToWorkTime() ?: 0
                            }
                        )
                        series(
                            List(days.size) { index ->
                                val dayDate = days[index]
                                val nightWorkItem = nightWork.find { it.date.formatMillisToDate() == dayDate }
                                nightWorkItem?.workTime?.convertLongToWorkTime() ?: 0
                            }
                        )
                    }
                }
            }
        }
    }
    ExampleChart(
        modifier = modifier,
        modelProducer = modelProducer,
        bottomAxisValue = bottomAxisValue
    )
}


@Composable
fun ExampleChart(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    bottomAxisValue: CartesianValueFormatter
) {
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
                valueFormatter = bottomAxisValue
            ),
            marker = marker,
            legend = rememberLegend(),
            decorations = listOf(
                rememberHorizontalLine()
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
                label = ShiftType.namesToList()[index],
            )
        },
        iconSize = 8.dp,
        iconPadding = 8.dp,
        spacing = 4.dp,
        padding = Dimensions.of(top = 8.dp, start = 12.dp),
    )


@Composable
private fun rememberHorizontalLine() =
    rememberHorizontalLine(
        y = {
            4.0
        },
        line = rememberLineComponent(
            color = colorResource(id = R.color.black).copy(alpha = 0.3f),
            thickness = 2.dp
        ),
        labelComponent =
        rememberTextComponent(
            margins = Dimensions.of(5.dp),
            padding =
            Dimensions.of(
                5.dp,
                5.dp,
            ),
            background = rememberShapeComponent(colorResource(id = R.color.black).copy(alpha = 0.3f), Shape.Pill),
        )
    )

private const val COLUMN_THICKNESS = 16
private val chartColors = listOf(
    Color(0xffb983ff),
    Color(0xff91b1fd),
    Color(0xff8fdaff)
)