package com.example.worktimetracker.ui.screens.worktime.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.util.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
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
                lineSeries {
                    series(
                        x, x.map { Random.nextFloat() * 15 }
                    )
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
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    rememberLine(
                        DynamicShader.color(colorResource(id = R.color.blue)),
                        pointConnector = remember {
                            LineCartesianLayer.PointConnector.cubic(0f)
                        }
                    )
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(guideline = null),
            marker = marker,
            persistentMarkers = rememberExtraLambda(marker) { marker at 7f },
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false)
    )

}

private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
    daysOfWeek[x.toInt() % daysOfWeek.size]
}
private val x = (1..50).toList()
