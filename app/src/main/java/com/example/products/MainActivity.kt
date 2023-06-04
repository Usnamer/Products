package com.example.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.products.screens.create.AddProductScreen
import com.example.products.screens.products.ProductsScreen
import com.example.products.ui.theme.ProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ProductsTheme {
                NavHost(navController = navController, startDestination = "products") {
                    composable("products") {
                        ProductsScreen(productsViewModel = hiltViewModel(), onProductClick = {}, onAddProduct = {
                            navController.navigate("addProduct")
                        })
                    }
                    composable("addProduct") {
                        AddProductScreen(addProductViewModel = hiltViewModel()) {
                            navController.navigateUp()
                        }
                    }
                    composable("products/{id}") {

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
    ProductsTheme {
        Greeting("Android")
    }
}