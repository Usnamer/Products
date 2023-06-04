package com.example.products.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.database.ProductDao
import com.example.products.database.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productsDao: ProductDao
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    private val _nameError = MutableStateFlow(false)
    val nameError = _nameError.asStateFlow()

    private val _manufacturer = MutableStateFlow("")
    val manufacturer = _manufacturer.asStateFlow()
    private val _manufacturerError = MutableStateFlow(false)
    val manufacturerError = _manufacturerError.asStateFlow()


    private val _price = MutableStateFlow(0.0)
    val price = _price.asStateFlow()
    private val _priceError = MutableStateFlow(false)
    val priceError = _priceError.asStateFlow()


    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()
    private val _dateError = MutableStateFlow(false)
    val dateError = _dateError.asStateFlow()


    fun setName(value: String) {
        _name.update {
            value
        }
        _nameError.update {
            false
        }
    }

    fun setManufacturer(value: String) {
        _manufacturer.update {
            value
        }
        _manufacturerError.update {
            false
        }
    }

    fun setPrice(value: String) {
        _price.update {
            value.toDoubleOrNull() ?: 0.0
        }
        _priceError.update {
            false
        }
    }

    fun setDateOfIssue(value: String) {
        _date.update {
            value
        }
        _dateError.update {
            false
        }
    }

    fun addProduct(onAdded: () -> Unit) {
        when {
            _name.value.isEmpty() -> _nameError.update { true }
            _manufacturer.value.isEmpty() -> _manufacturerError.update { true }
            _price.value.isNaN() -> _priceError.update { true }
            !_date.value.matches(Regex("\\d\\d.\\d\\d.\\d\\d\\d\\d")) -> _dateError.update { true }
        }
        if (!_nameError.value && !_manufacturerError.value && !_priceError.value && !_dateError.value) {
            val product = ProductEntity(
                0,
                name.value,
                price.value,
                manufacturer.value,
                date.value
            )
            viewModelScope.launch {
                productsDao.insertProduct(product)
                onAdded()
            }
        }
    }
}