package com.example.shop

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shop.ui.theme.ShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopTheme {
                Surface(){
                    ShoppingApp(products)
                }
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val desc: String,
)

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C."),
    Product("Product D", "$200", "Premium product D."),
    Product("Product E", "$200", "Premium product E.")
)

@Composable
fun ShoppingApp(products: List<Product>) {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }

    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(modifier = Modifier.fillMaxSize() .absolutePadding(left = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Box(modifier = Modifier.weight(1f)) {
                ProductList(products = products) { product ->
                    selectedProduct.value = product
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                ProductDetails(product = selectedProduct.value, onBack = { selectedProduct.value = null })
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (selectedProduct.value == null) {
                ProductList(products = products) { product ->
                    selectedProduct.value = product
                }
            } else {
                ProductDetails(product = selectedProduct.value, onBack = { selectedProduct.value = null })
            }
        }

    }
}



@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onProductClick(product) },
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?, onBack: () -> Unit) {

    val orientation = LocalConfiguration.current.orientation

    Column(modifier = Modifier.padding(16.dp)) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

    if (product != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Price: ${product.price}", style = MaterialTheme.typography.labelSmall)
            Text(text = product.desc, style = MaterialTheme.typography.bodySmall)
        }
    } else {
        Text(
            text = "Select a product to view details.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}}

