package com.example.products.screens.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.products.database.ProductEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductsScreen(
    productsViewModel: ProductsViewModel,
    onProductClick: (ProductEntity) -> Unit,
    onAddProduct: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Товары") })
        }
    ) { paddings ->
        val products by productsViewModel.filteredProducts.collectAsState()
        val filterValue by productsViewModel.filterValue.collectAsState()
        LazyColumn(
            modifier = Modifier.padding(paddings),
            contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = filterValue,
                        onValueChange = productsViewModel::setFilterValue,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .weight(1f),
                        label = {
                            Text("Фильтр")
                        }
                    )
                    IconButton(onClick = onAddProduct) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
            items(products) {
                ProductItem(product = it, onProductClick = onProductClick, productsViewModel::deleteProduct)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductItem(
    product: ProductEntity,
    onProductClick: (ProductEntity) -> Unit,
    onDeleteClick: (ProductEntity) -> Unit
) {
    Card(onClick = { onProductClick(product) }, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Цена: ${product.price}", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Производитель: ${product.manufacturer}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Дата изготовления: ${product.dateOfIssue}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            IconButton(onClick = { onDeleteClick(product) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}