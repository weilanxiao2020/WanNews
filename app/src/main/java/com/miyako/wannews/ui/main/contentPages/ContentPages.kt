package com.miyako.wannews.ui.main.contentPages

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import com.miyako.wannews.ui.theme.SmallPadding
import com.miyako.wannews.ui.theme.listTitle
import com.miyako.wannews.ui.theme.textPrimary
import java.util.*

/**
* @Description
* @Author Miyako
* @Date 2021-08-26-0026
*/
const val TAG = "ContentPages"

//region News
@Composable
fun NewsScreen(viewModel: ContentPagesViewModel = ContentPagesViewModel()) {
    LogUtils.d(TAG, "compose")

    val content = viewModel.getNewsPage().collectAsLazyPagingItems()
    val flag = remember {
        mutableStateOf(false)
    }
    if (!flag.value) {
        flag.value = true
        // viewModel.getNews()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Text(
            text = stringResource(id = R.string.rb_bottom_news_label),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    viewModel.getNewsPage()
                }
        )
        // LogUtils.d(TAG, "content ${content.value?.size}")
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = {
                // viewModel.pager.c().refresh()
            },
        ) {
            LazyColumn(Modifier.fillMaxHeight()) {
                content.apply {
                    items(content) {
                        Text(text = it!!.title, style = MaterialTheme.typography.listTitle,
                            color = MaterialTheme.colors.textPrimary, modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp))
                        Column(
                            Modifier
                                .padding(4.dp, 0.dp, 4.dp, 0.dp)
                                .fillMaxWidth()) {
                            Text(text = it.author,
                                color = MaterialTheme.colors.textPrimary, modifier = Modifier
                                    .padding(4.dp, 0.dp, 4.dp, 0.dp)
                                    .wrapContentWidth())
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Text(text = SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Date(it.publishTime)),
                                    color = MaterialTheme.colors.textPrimary, modifier = Modifier.wrapContentWidth(Alignment.End))
                            }
                        }

                        Divider(modifier = Modifier.padding(0.dp, 4.dp))
                    }
                }
            }
        }
    }
}
//endregion


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NewsScreen()
}

@Composable
fun ProjectScreen(viewModel: ContentPagesViewModel = ContentPagesViewModel()) {
    // Column(
    //     modifier = Modifier
    //         .fillMaxSize()
    //         .background(MaterialTheme.colors.background)
    //         .wrapContentSize(Alignment.Center)
    // ) {
    //     Text(
    //         text = stringResource(id = R.string.rb_bottom_project_label),
    //         fontWeight = FontWeight.Bold,
    //         color = MaterialTheme.colors.primary,
    //         modifier = Modifier.align(Alignment.CenterHorizontally),
    //         textAlign = TextAlign.Center,
    //         fontSize = 25.sp
    //     )
    // }
    val project = viewModel.getProjectPage(294).collectAsLazyPagingItems()
    val projectClass = viewModel.getProjectClass().collectAsState(listOf())

    val flag = remember {
        mutableStateOf(false)
    }
    if (!flag.value) {
        flag.value = true
        // viewModel.getNews()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        LazyRow(Modifier.fillMaxWidth().padding(SmallPadding)) {
            items(projectClass.value.size) { index ->
                Text(text = projectClass.value[index].name, style = MaterialTheme.typography.listTitle,
                    color = MaterialTheme.colors.textPrimary, modifier = Modifier.padding(4.dp, 0.dp))
            }
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = {
                // viewModel.pager.c().refresh()
            },
        ) {
            LazyColumn(Modifier.fillMaxHeight()) {
                project.apply {
                    items(project) {
                        Text(text = it!!.title, style = MaterialTheme.typography.listTitle,
                            color = MaterialTheme.colors.textPrimary, modifier = Modifier.padding(4.dp, 0.dp))
                        Column(
                            Modifier
                                .padding(4.dp, 0.dp)
                                .fillMaxWidth()) {
                            Text(text = it.author,
                                color = MaterialTheme.colors.textPrimary, modifier = Modifier
                                    .padding(4.dp, 0.dp)
                                    .wrapContentWidth())
                        }
                        Divider(modifier = Modifier.padding(0.dp, 4.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MusicScreenPreview() {
    ProjectScreen()
}

@Composable
fun PublicScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(id = R.string.rb_bottom_public_label),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    PublicScreen()
}


@Composable
fun PartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(id = R.string.rb_bottom_part_label),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    PartScreen()
}

@Composable
fun MineScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(id = R.string.rb_bottom_mine_label),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MineScreen()
}
