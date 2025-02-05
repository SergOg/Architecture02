package ru.gb.android.workshop2.presentation.list.promo

import android.content.Context

data class ScreenState(
    val isLoading: Boolean = false,
    val promoState: PromoState = PromoState(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = { "" }
)

data class PromoState(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
)
