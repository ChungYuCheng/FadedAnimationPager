package com.example.fadedanimationpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fadedanimationpager.ui.theme.FadedAnimationPagerTheme
import com.momo.mobile.shoppingv2.android.compose.image.HorizontalPagerWithFadeTransition
import com.momo.mobile.shoppingv2.android.compose.image.rangeForRandom

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FadedAnimationPagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        HorizontalPagerWithFadeTransition(
                            Modifier
                                .padding(innerPadding)
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

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FadedAnimationPagerTheme {
        Greeting("Android")
    }
}