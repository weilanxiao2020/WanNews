package com.miyako.wannews.ui.main.HomeScreen

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.miyako.architecture.util.DateUtils
import com.miyako.wannews.R
import com.miyako.wannews.entity.HomeArticleEntity
import com.miyako.wannews.ui.article.ArticleActivity
import com.miyako.wannews.ui.theme.*

/**
 * @Description 首页内容，包括轮播图，最新文章列表
 * @Author Miyako
 * @Date 2021-10-10-0010
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = HomeScreenViewModel(),
    context: Context
) {
    val topArticle = viewModel.getHomeTopArticle().collectAsState(listOf())
    val articleList = viewModel.getHomeArticlePage().collectAsLazyPagingItems()

    val flag = remember {
        mutableStateOf(false)
    }
    if (!flag.value) {
        flag.value = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Text(
            text = stringResource(id = R.string.rb_bottom_index_label),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    viewModel.getHomeArticlePage()
                }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = {
                // viewModel.pager.c().refresh()
                viewModel.getHomeArticlePage()
            },
        ) {
            LazyColumn(Modifier.fillMaxHeight()) {
                items(topArticle.value.size + articleList.itemCount ) { idx ->
                    val article = if (idx < topArticle.value.size) topArticle.value[idx]
                    else articleList[idx - topArticle.value.size]
                    article?.let {
                        HomeItem(
                            modifier = Modifier,
                            itemData = it) {
                            val intent = Intent(context, ArticleActivity::class.java)
                            intent.putExtra(ArticleActivity.KEY_URL, it.link)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeItem(modifier: Modifier = Modifier,
             itemData: HomeArticleEntity,
             click: () -> Unit) {
    Column(
        modifier = modifier
            // .clickable {
            //     click()
            // }
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    click()
                }
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
            ) {
                Text(text = itemData.author,
                    style = MaterialTheme.typography.articleAuthor,
                    color = MaterialTheme.colors.guideBack,
                    modifier = Modifier
                        .weight(1f))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Text(text = DateUtils.toDate(itemData.publishTime),
                        color = MaterialTheme.colors.guideBack,
                        modifier = Modifier.wrapContentWidth(Alignment.End))
                }
            }
            Text(text = itemData.title,
                style = MaterialTheme.typography.articleTitle,
                color = MaterialTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(vertical = 4.dp))
            Text(
                text = itemData.chapterName)
        }
        Divider()
    }
}