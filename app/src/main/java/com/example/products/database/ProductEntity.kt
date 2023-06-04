package com.example.products.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.products.models.Product

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val manufacturer: String,
    val dateOfIssue: String,
)

fun ProductEntity.toProduct() = Product(
    id, name, price, manufacturer, dateOfIssue
)

fun Product.toEntity() = ProductEntity(
    id, name, price, manufacturer, dateOfIssue
)

