package com.miyako.wannews.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.res.painterResource
import com.miyako.wannews.R

@Deprecated(message = "废弃")
class MainComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_compose)
        setContent {
            TextUI()
            Row {
                Text(text = "123")
                Text(text = "asd")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "button")
                }
            }

        }
        // setContent {
        //    Text()
        // }
    }

    @Preview
    @Composable
    fun TextUI() {
        Row {
            Text(text = "123")
            Text(text = "asd")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "button")
            }
        }
    }
}