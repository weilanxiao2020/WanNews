package com.miyako.wannews.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import com.miyako.wannews.base.BaseActivity
import com.miyako.wannews.ui.theme.Blue
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.miyako.wannews.base.MyApplication.Companion.TAG
import com.miyako.wannews.ui.main.contentPages.*

class MainActivity : BaseActivity() {

    lateinit var newsFragment: NewsFragment

    val newResId = ResId(R.mipmap.ic_rb_bottom_news, R.mipmap.ic_rb_bottom_news_selected)
    val newColor = TextColor(Color.Black, Color.Blue)

    val projectResId = ResId(R.mipmap.ic_rb_bottom_project, R.mipmap.ic_rb_bottom_project_selected)
    val projectColor = TextColor(Color.Black, Color.Blue)

    val partResId = ResId(R.mipmap.ic_rb_bottom_part, R.mipmap.ic_rb_bottom_part_selected)
    val partColor = TextColor(Color.Black, Color.Blue)

    val publicResId = ResId(R.mipmap.ic_rb_bottom_public, R.mipmap.ic_rb_bottom_public_selected)
    val publicColor = TextColor(Color.Black, Color.Blue)

    val mineResId = ResId(R.mipmap.ic_rb_bottom_mine, R.mipmap.ic_rb_bottom_mine_selected)
    val mineColor = TextColor(Color.Black, Color.Blue)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            // val newsMenu = remember {
            //     mutableStateOf(MenuState(true, newResId, newColor))
            // }
            // val projectMenu = remember {
            //     mutableStateOf(MenuState(false, projectResId, projectColor))
            // }
            // val partMenu = remember {
            //     mutableStateOf(MenuState(false, partResId, partColor))
            // }
            // val publicMenu = remember {
            //     mutableStateOf(MenuState(false, publicResId, publicColor))
            // }
            // val mineMenu = remember {
            //     mutableStateOf(MenuState(false, mineResId, mineColor))
            // }
            //
            // val menuIdx = remember {
            //     mutableStateOf(0)
            // }

            // Row() {
            //     LogUtils.d(TAG, "recompose")
            //     bottomMenu(getString(R.string.rb_bottom_news_label),
            //         newsMenu.value.resId(),
            //         newsMenu.value.textColor(),
            //         Modifier.clickable {
            //             menuIdx.value = 0
            //             changeMenu(newsMenu.value)
            //         })
            //     bottomMenu(getString(R.string.rb_bottom_project_label),
            //         projectMenu.value.resId(),
            //         projectMenu.value.textColor(),
            //         Modifier.clickable {
            //             menuIdx.value = 1
            //             changeMenu(projectMenu.value)
            //         })
            //     bottomMenu(getString(R.string.rb_bottom_part_label),
            //         partMenu.value.resId(),
            //         partMenu.value.textColor(),
            //         Modifier.clickable {
            //             menuIdx.value = 2
            //             changeMenu(partMenu.value)
            //         })
            //     bottomMenu(getString(R.string.rb_bottom_public_label),
            //         publicMenu.value.resId(),
            //         publicMenu.value.textColor(),
            //         Modifier.clickable {
            //             menuIdx.value = 3
            //             changeMenu(publicMenu.value)
            //         })
            //     bottomMenu(getString(R.string.rb_bottom_mine_label),
            //         mineMenu.value.resId(),
            //         mineMenu.value.textColor(),
            //         Modifier.clickable {
            //             menuIdx.value = 4
            //             changeMenu(mineMenu.value)
            //         })
            // }
        }
    }

    class ResId(val uncheckId: Int, val checkId: Int) {}
    class TextColor(val uncheckColor: Color, val checkColor: Color) {}
    class MenuState(var isChecked: Boolean, val resId: ResId, val textColor: TextColor) {
        fun resId(): Int {
            val res = if (isChecked) {
                 resId.checkId
            } else {
                resId.uncheckId
            }
            LogUtils.d("MenuState", "resId:${res}")
            return res
        }
        fun textColor(): Color {
            val res = if (isChecked) {
                textColor.checkColor
            } else {
                textColor.uncheckColor
            }
            LogUtils.d("MenuState", "textColor:${res}")
            return res
        }

        override fun equals(other: Any?): Boolean {
            return isChecked === (other as MenuState).isChecked
        }
    }

    fun changeMenu(menustate: MenuState) {
        LogUtils.d(TAG, "menu is checked:"+menustate.isChecked)
        menustate.isChecked = !menustate.isChecked
        LogUtils.d(TAG, "after menu is checked:"+menustate.isChecked)
    }

    override fun getLayoutId() : Int{
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        switchBottomBar(0)
        rb_news_page.setOnClickListener { switchBottomBar(0) }
        rb_project_page.setOnClickListener { switchBottomBar(1) }
        rb_part_page.setOnClickListener { switchBottomBar(2) }
        rb_public_page.setOnClickListener { switchBottomBar(3) }
        rb_mine_page.setOnClickListener { switchBottomBar(4) }
    }


    var idx = 0
    fun switchBottomBar(idx: Int) {
        this.idx = idx
        LogUtils.d(TAG, "底部导航栏切换：${idx}")
        when (idx) {
            4 -> {
                rb_mine_page.isChecked = true
                getArticle(4)
            }
            3 -> {
                rb_public_page.isChecked = true
                getArticle(3)
            }
            2 -> {
                rb_part_page.isChecked = true
                getArticle(2)
            }
            1 -> {
                rb_project_page.isChecked = true
                getArticle(1)
            }
            else -> {
                rb_news_page.isChecked = true
                getArticle(0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        requestData()
    }

    fun getArticle(idx :Int) {

        newsFragment = NewsFragment()
        // supportFragmentManager
        //     .beginTransaction()
        //     .add(R.id.home_container, newsFragment)
        //     .commitAllowingStateLoss()
//        val repos: Call<ArticleDto> = httpRequest.getArticleService().getArticle(idx)
//        LogUtils.d(TAG, "retrofit")
//        repos.enqueue(object : retrofit2.Callback<ArticleDto>{
//            override fun onFailure(call: Call<ArticleDto>, t: Throwable) {
//                LogUtils.e(TAG, "error")
//                LogUtils.d(TAG, call.toString())
//            }
//
//            override fun onResponse(call: Call<ArticleDto>, response: Response<ArticleDto>) {
//                LogUtils.d(TAG, "success")
//                LogUtils.d(TAG, response.body().toString())
//            }
//
//        })
    }

    override fun requestData() {
        getArticle(0)
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController)
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.theme_color),
        contentColor = Color.White
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    val viewModel = remember {
        ContentPagesViewModel()
    }
    NavHost(navController, startDestination = NavigationItem.News.route) {
        composable(NavigationItem.News.route) {
            NewsScreen(viewModel)
        }
        composable(NavigationItem.Project.route) {
            ProjectScreen(viewModel)
        }
        composable(NavigationItem.Part.route) {
            PartScreen()
        }
        composable(NavigationItem.Public.route) {
            PublicScreen()
        }
        composable(NavigationItem.Mine.route) {
            MineScreen()
        }
    }
}

const val KEY_ROUTE = "key_route"

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.News,
        NavigationItem.Project,
        NavigationItem.Part,
        NavigationItem.Public,
        NavigationItem.Mine
    )

    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        var currentRoute by remember {
            mutableStateOf(NavigationItem.News.route)
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        LogUtils.d(TAG, "current route:${currentRoute}")
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                icon = {
                    if (currentRoute == item.route) {
                        Image(painterResource(id = item.selectIcon), contentDescription = "")
                    } else {
                        Image(painterResource(id = item.normalIcon), contentDescription = "")
                    }
                },
                    label = {
                        if (currentRoute == item.route) {
                            Text(text = item.title, color = Blue)
                        } else {
                            Text(text = item.title, color = Color.Gray)
                        }
                            },
                    // selectedContentColor = Color.White.copy(0.6f),
                    // unselectedContentColor = Red,
                    alwaysShowLabel = true,
                    onClick = {
                        /* Add code later */
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navBackStackEntry?.arguments?.putString(KEY_ROUTE, item.route)
                            LogUtils.d(TAG, "click route:${item.route}")
                            currentRoute = item.route
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // // Avoid multiple copies of the same destination when
                            // // reselecting the same item
                            // launchSingleTop = true
                            // // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
            )
        }
    }
}


@Preview
@Composable
fun bottomMenu() {
    val isChecked = remember {
        mutableStateOf(true)
    }
    val resId = remember {
        mutableStateOf(R.mipmap.ic_rb_bottom_news)
    }
    val textColor = remember {
        mutableStateOf(Color.Black)
    }
    bottomMenu("首s页",
        resId.value,
        textColor.value,
        Modifier.clickable {
            isChecked.value = !isChecked.value
            if (isChecked.value) {
                resId.value = R.mipmap.ic_rb_bottom_news_selected
                textColor.value = Color.Red
            } else {
                resId.value = R.mipmap.ic_rb_bottom_news
                textColor.value = Color.Black
            }
        })

}

@Composable
fun bottomMenu(menu: String, resId: Int, textColor: Color, modifier: Modifier) {
    Box {
        Column(modifier = modifier) {
            Image(painterResource(id = resId),
                contentDescription = "",
                Modifier.align(Alignment.CenterHorizontally))
            Text(text = menu,
                fontSize = 12.sp,
                style = TextStyle(color = textColor)
            )
        }
    }
}