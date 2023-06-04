package com.example.products.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.database.ProductDao
import com.example.products.database.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsDao: ProductDao
) : ViewModel() {

    private val products = productsDao.products

    private val _filterValue = MutableStateFlow("")
    val filterValue = _filterValue.asStateFlow()

    val filteredProducts = products.combine(filterValue) { products, filter ->
        if (filter.isNotEmpty()) {
            products.filter {
                it.name.contains(filter, true)
            }
        } else {
            products
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        listOf()
    )

    fun addProduct(product: ProductEntity) {
        viewModelScope.launch {
            productsDao.insertProduct(product)
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            productsDao.deleteProduct(product)
        }
    }

    fun setFilterValue(value: String) {
        _filterValue.update {
            value
        }
    }
}