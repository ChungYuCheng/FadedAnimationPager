package com.momo.mobile.shoppingv2.android.compose.image

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 純圓點指示器
 * 商品圖Pager指示器
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedPageIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    visibleDots: Int = 6, // 你希望在一行中显示的最大点数
    nonSelectedWidth: Dp = 3.dp,
    selectedWidth: Dp = 5.dp,
    spacing: Dp = 4.dp // 新增的spacing参数
) {
    val indicatorScrollState = rememberLazyListState()

    LaunchedEffect(key1 = pagerState.currentPage, block = {
        val currentPage = pagerState.currentPage
        val size = indicatorScrollState.layoutInfo.visibleItemsInfo.size
        val lastVisibleIndex =
            indicatorScrollState.layoutInfo.visibleItemsInfo.last().index
        val firstVisibleItemIndex = indicatorScrollState.firstVisibleItemIndex

        if (currentPage > lastVisibleIndex - 1) {
            indicatorScrollState.animateScrollToItem(currentPage - size + 2)
        } else if (currentPage <= firstVisibleItemIndex + 1) {
            indicatorScrollState.animateScrollToItem(Math.max(currentPage - 1, 0))
        }
    })

    val width = ((nonSelectedWidth + spacing * 2 ) * (visibleDots - 1)) + (selectedWidth + (spacing * 2))
    LazyRow(
        state = indicatorScrollState,
        modifier = modifier
            .width(width),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.Red else Color.LightGray
            item(key = "item$iteration") {
                val currentPage = pagerState.currentPage
                val firstVisibleIndex by remember { derivedStateOf { indicatorScrollState.firstVisibleItemIndex } }
                val lastVisibleIndex = indicatorScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val size by animateDpAsState(
                    targetValue = if (iteration == currentPage) {
                        selectedWidth
                    } else if (iteration in firstVisibleIndex + 1..lastVisibleIndex - 1) {
                        nonSelectedWidth
                    } else {
                        nonSelectedWidth
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = spacing) // 使用spacing参数，使点之间的距离可调
                        .background(color, CircleShape)
                        .size(size)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun AnimatedPageIndicatorPreview() {
    AnimatedPageIndicator(pagerState = rememberPagerState(pageCount = { 10 }))
}

/**
 * 長點+圓點的Pager指示器
 * 類似版位『大家都在搜』指示器的效果
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WormIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    spacing: Dp = 5.dp,
    dotWidth: Dp = 5.dp,
    dotHeight: Dp = 5.dp,
    longdotWidth: Dp = 10.dp,
    inactiveColor: Color = Color.Gray,
) {
    val distance = with(LocalDensity.current) { (dotWidth + spacing).toPx() }

    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pagerState.pageCount) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(width = dotWidth, height = dotHeight)
                        .background(color = inactiveColor)
                )
            }
        }

        Box(
            modifier = Modifier
                .slidingLineTransition(pagerState, distance, (longdotWidth - dotWidth) / 2)
                .size(width = longdotWidth, height = dotHeight)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(10.dp),
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.slidingLineTransition(
    pagerState: PagerState,
    distance: Float,
    backwardDistance: Dp
) = graphicsLayer {
    val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
    translationX = scrollPosition * distance - backwardDistance.toPx()
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun WormPageIndicatorPreview() {
    WormIndicator(pagerState = rememberPagerState(pageCount = { 10 }))
}