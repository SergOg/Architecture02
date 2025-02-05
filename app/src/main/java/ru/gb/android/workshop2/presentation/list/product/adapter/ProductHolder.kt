package ru.gb.android.workshop2.presentation.list.product.adapter

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.workshop2.R
import ru.gb.android.workshop2.databinding.ItemProductBinding
import ru.gb.android.workshop2.presentation.list.product.ProductState

class ProductHolder(
    private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductState) {
        binding.image.load(product.image)
        binding.name.text = product.name
        binding.price.text =
            binding.root.resources.getString(R.string.price_with_arg, product.price)
        if(product.hasDiscount) {
            binding.promo.visibility = VISIBLE
            binding.promo.text = product.discount
        } else {
            binding.promo.visibility = GONE
        }
    }
}
