package com.example.products.screens.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    addProductViewModel: AddProductViewModel,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Добавить товар")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        val name by addProductViewModel.name.collectAsState()
        val nameError by addProductViewModel.nameError.collectAsState()
        val manufacturer by addProductViewModel.manufacturer.collectAsState()
        val manufacturerError by addProductViewModel.manufacturerError.collectAsState()
        val price by addProductViewModel.price.collectAsState()
        val priceError by addProductViewModel.priceError.collectAsState()
        val date by addProductViewModel.date.collectAsState()
        val dateError by addProductViewModel.dateError.collectAsState()
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = addProductViewModel::setName, label = {
                    Text(
                        text = "Наименование"
                    )
                },
                isError = nameError,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = manufacturer,
                onValueChange = addProductViewModel::setManufacturer, label = {
                    Text(
                        text = "Производитель"
                    )
                },
                isError = manufacturerError,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price.toString(),
                onValueChange = addProductViewModel::setPrice, label = {
                    Text(
                        text = "Цена"
                    )
                },
                isError = priceError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = addProductViewModel::setDateOfIssue, label = {
                    Text(
                        text = "Дата изготовления"
                    )
                },
                isError = dateError,
                placeholder = {
                    Text("ДД/ММ/ГГГГ")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { addProductViewModel.addProduct(onBackClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить")
            }
        }
    }
}