package ru.gb.android.workshop2.presentation.card.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductViewModelFactory(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(
            consumeFirstProductUseCase = consumeFirstProductUseCase,
            productStateFactory = productStateFactory,
            consumePromosUseCase = consumePromosUseCase,
        ) as T
    }
}