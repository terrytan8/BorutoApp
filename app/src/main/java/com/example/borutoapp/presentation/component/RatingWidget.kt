package com.example.borutoapp.presentation.component

import android.graphics.Rect
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.LightGray
import com.example.borutoapp.ui.theme.starColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2f
) {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor)
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBound: androidx.compose.ui.geometry.Rect,
    scaleFactor: Float
) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            //Calculation to move the path to middle
            val pathWidth = starPathBound.width
            val pathHeight = starPathBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            /**Translate to move the star to middle of the canvas**/
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = starColor
                )
            }
        }

    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBound: androidx.compose.ui.geometry.Rect,
    scaleFactor: Float
) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            //Calculation to move the path to middle
            val pathWidth = starPathBound.width
            val pathHeight = starPathBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            /**Translate to move the star to middle of the canvas**/
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = starColor,
                        size = Size(
                            width = starPathBound.maxDimension / 1.7f,
                            height = starPathBound.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBound: androidx.compose.ui.geometry.Rect,
    scaleFactor: Float
) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = this.size
        scale(scale = scaleFactor) {
            //Calculation to move the path to middle
            val pathWidth = starPathBound.width
            val pathHeight = starPathBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            /**Translate to move the star to middle of the canvas**/
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }

    }
}


@Preview
@Composable
fun FilledStarPreview() {
    RatingWidget(modifier = Modifier, rating = 1.0)
}

@Preview
@Composable
fun HalfFilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember  {
        starPath.getBounds()
    }
    HalfFilledStar(starPath = starPath, starPathBound = starPathBounds, 3f)
}

@Preview
@Composable
fun EmptyStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(starPath = starPath, starPathBound = starPathBounds, 3f)
}