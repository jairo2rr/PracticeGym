package com.jairorr.practicegym.presentation.transition

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jairorr.practicegym.data.transition.ItemCard
import com.jairorr.practicegym.data.transition.Options
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    items: List<ItemCard>,
    saveAnswer: (Options) -> Unit,
    viewModel: CardViewModel,
) {
    val selectedIndex by viewModel.currentPosition.observeAsState(0)
    val lazyListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    val coroutineScope = rememberCoroutineScope()
    val firstVisibleItemIndex = remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(key1 = selectedIndex) {
        lazyListState.animateScrollToItemCenter(selectedIndex+1)
        Log.d(
            "CardContent::indexPassed",
            "Visible index: ${firstVisibleItemIndex.value} ~ Selected index: $selectedIndex"
        )
    }

    LaunchedEffect(key1 = firstVisibleItemIndex.value) {
//        if(firstVisibleItemIndex.value + 1 != selectedIndex){
//            changeIndex(firstVisibleItemIndex.value)
//        }
        coroutineScope.launch {
            lazyListState.animateScrollToItemCenter(firstVisibleItemIndex.value+1)
        }
        viewModel.updatePosition(firstVisibleItemIndex.value)
        Log.d(
            "CardContent::firstVisibleItemIndex",
            "Visible index: ${firstVisibleItemIndex.value} ~ Selected index: ${selectedIndex}"
        )
    }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = 12.dp),
        state = lazyListState,
        flingBehavior = snapFlingBehavior
    ) {
        item {
            Box(modifier = Modifier.width(300.dp))
        }
        itemsIndexed(items) { index, item ->
            Card(modifier = Modifier
                .width(300.dp)
                .background(Color.Cyan)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = item.subtitle, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text(text = item.question, fontStyle = FontStyle.Italic)
                    Column {
                        item.options.forEachIndexed { i, option ->
                            Row {
                                RadioButton(selected = false, onClick = { })
                                Text(text = option.description)
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun LazyListState.animateScrollToItemCenter(index: Int) {
//    layoutInfo.resolveItemOffsetToCenter(index)?.let {
//        animateScrollToItem(index, it)
//        return
//    }
//
//    scrollToItem(index)
//
//    layoutInfo.resolveItemOffsetToCenter(index)?.let {
//        animateScrollToItem(index, it)
//    }
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    val containerSize = layoutInfo.viewportSize.width - layoutInfo.beforeContentPadding - layoutInfo.afterContentPadding

    if (itemInfo != null) {
        val offset = -(containerSize - itemInfo.size) / 2
        animateScrollToItem(index, offset)
    } else {
        scrollToItem(index)
        val updatedItemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
        if (updatedItemInfo != null) {
            val updatedOffset = -(containerSize - updatedItemInfo.size) / 2
            animateScrollToItem(index, updatedOffset)
        }
    }
}

private fun LazyListLayoutInfo.resolveItemOffsetToCenter(index: Int): Int? {
    val itemInfo = visibleItemsInfo.firstOrNull { it.index == index } ?: return null
    val containerSize = viewportSize.width - beforeContentPadding - afterContentPadding
    return -(containerSize - itemInfo.size) / 2
}