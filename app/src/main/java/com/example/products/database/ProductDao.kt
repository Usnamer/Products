package com.example.products.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @get:Query("SELECT * FROM ProductEntity")
    val products: Flow<List<ProductEntity>>

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)
}