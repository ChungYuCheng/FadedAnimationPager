package com.momo.mobile.shoppingv2.android.compose.image


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

val rangeForRandom = (0..100000)

@Preview
@Composable
fun HorizontalPagerWithFadeTransitionPreview() {
    HorizontalPagerWithFadeTransition(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        imagesList = listOf(
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
            "https://picsum.photos/seed/${rangeForRandom.random()}/300/500",
        )
    )
}

@Composable
fun HorizontalPagerWithFadeTransition(modifier: Modifier = Modifier, imagesList: List<String> = listOf()) {

    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            FadedPic(modifier, imagesList)
        }
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            FadedPic(modifier, imagesList)

        }
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            FadedPic(modifier, imagesList, type = 1)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FadedPic(modifier: Modifier, imagesList: List<String> = listOf(), type: Int = 0) {

    val pagerState = rememberPagerState(pageCount = { imagesList.size })

    val interactionSource = remember { MutableInteractionSource() }
    val interactionState by interactionSource.interactions.collectAsState(initial = null)
    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = interactionState, key2 = pagerIsDragged) {
        if (!pagerIsDragged && interactionState !is PressInteraction.Press) {
            while (true) {
                delay(3000)
                pagerState.animateScrollToPage(
                    (pagerState.currentPage + 1) % pagerState.pageCount,
                    animationSpec =  tween(durationMillis = 1000, easing = LinearEasing)
                )
            }
        }
    }

    Box {
        HorizontalPager(
            modifier = modifier.fillMaxSize(),
            state = pagerState,
            beyondBoundsPageCount = 2
        ) { page ->
            Box(
                Modifier
                    .pagerFadeTransition(page, pagerState)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            imagesList[page]
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = "media",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
        ) {
            if (type == 0) {
                AnimatedPageIndicator(
                    pagerState = pagerState
                )
            } else {
                WormIndicator(
                    pagerState = pagerState,
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        translationX = pageOffset * size.width
        alpha = 1 - pageOffset.absoluteValue
    }

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

