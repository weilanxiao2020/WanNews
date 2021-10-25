package com.miyako.wannews.ui.main.GuideScreen

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.miyako.wannews.R
import com.miyako.wannews.ui.main.HomeScreen.HomeScreenViewModel
import com.miyako.wannews.ui.theme.MiddleGray
import com.miyako.wannews.ui.theme.Purple200
import com.miyako.wannews.ui.theme.Purple500
import com.miyako.wannews.ui.theme.guideBack
import java.util.*

/**
 * @Description
 * @Author Miyako
 * @Date 2021-10-10-0010
 */
const val GuideClass = "GuideClass"
const val SystemTree = "SystemTree"

@Composable
fun GuideScreen(
    navController: NavController,
    viewModel: GuideScreenViewModel = GuideScreenViewModel(),
    modifier: Modifier = Modifier
) {
    val guideClassList = viewModel.getGuide().collectAsState(listOf())
    val systemTreeList = viewModel.getSystem().collectAsState(listOf())

    val map = remember {
        mutableMapOf(Pair(GuideClass, guideClassList), Pair(SystemTree, systemTreeList))
    }

    val select = remember {
        mutableStateOf(GuideClass)
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colors.guideBack)
        ) {
            item {
                Text(
                    text = "导航",
                    fontWeight = if (select.value == GuideClass) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = if (select.value == GuideClass) MaterialTheme.colors.primary else Purple200,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            select.value = GuideClass
                        })
            }
            item {
                Text(
                    text = "体系",
                    fontWeight = if (select.value == SystemTree) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color =  if (select.value == SystemTree) MaterialTheme.colors.primary else Purple200,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            select.value = SystemTree
                        })
            }
        }
        LazyColumn(
            modifier = modifier
                .weight(3f)
                .padding(horizontal = 2.dp)
        ) {
            val list = map[select.value]?.value
            list.let {
                items(list!!.size) { idx ->
                    Text(
                        text = list[idx].name,
                        fontSize = 20.sp)
                }
            }

        }
    }
}