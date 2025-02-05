package ru.gb.android.workshop2.presentation.list.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.databinding.FragmentProductListBinding
import ru.gb.android.workshop2.presentation.list.product.adapter.ProductsAdapter

class ProductListFragment : Fragment() {//, ProductListView {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductsAdapter()

    private val viewModel by viewModels<ProductViewModel> {
        FeatureServiceLocator.provideProductViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.loadProduct()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when {
                        state.hasError -> {
                            showError(state.getErrorText(requireContext()))
                            viewModel.errorShown()
                        }
                        state.isLoading -> renderLoading()
                        else -> renderProductState(state.productState)
                    }
                }
            }
        }
    }

    private fun renderProductState(productState: ProductState) {
        hideAll()
        with(productState) {
            binding.progress.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.submitList(productState)
        }
    }

    private fun renderLoading() {
        hideAll()
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideAll() {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun showProgress() {
//        binding.progress.visibility = View.VISIBLE
//    }
//
//    override fun hideProgress() {
//        binding.progress.visibility = View.GONE
//    }
//
//    override fun hidePullToRefresh() {
//        binding.swipeRefreshLayout.isRefreshing = false
//    }
//
//    override fun showProducts(productList: List<ProductVO>) {
//        binding.recyclerView.visibility = View.VISIBLE
//        adapter.submitList(productList)
//    }
//
//    override fun hideProducts() {
//        binding.recyclerView.visibility = View.GONE
//    }

    private fun showError(errorText: String) {
        Toast.makeText(
            requireContext(),
            errorText,
            Toast.LENGTH_SHORT
        ).show()
    }
}
