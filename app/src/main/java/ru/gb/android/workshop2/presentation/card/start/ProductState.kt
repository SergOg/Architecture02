package ru.gb.android.workshop2.presentation.card.start

import android.content.Context
import ru.gb.android.workshop2.presentation.card.start.ProductState

data class ScreenState(
    val isLoading: Boolean = false,
    val productState: ProductState = ProductState(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = {""}
)

data class ProductState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)