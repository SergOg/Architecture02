package ru.gb.android.workshop2.presentation.list.promo

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
import ru.gb.android.workshop2.databinding.FragmentPromoListBinding
import ru.gb.android.workshop2.presentation.list.promo.adapter.PromoAdapter

class PromoListFragment : Fragment(){//, PromoListView {

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()

    private val viewModel by viewModels<PromoViewModel> {
        FeatureServiceLocator.providePromoViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.loadPromos()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when {
                        state.hasError -> {
                            showError(state.getErrorText(requireContext()))
                            viewModel.errorShown()
                        }
                        state.isLoading -> renderLoading()
                        else -> renderPromoState(state.promoState)
                    }
                }
            }
        }
    }

    private fun renderPromoState(promoState: PromoState) {
        hideAll()
        with(promoState) {
            binding.progress.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.submitList(promoState)
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
//    override fun showPromos(promoList: List<PromoVO>) {
//        binding.recyclerView.visibility = View.VISIBLE
//        adapter.submitList(promoList)
//    }
//
//    override fun hidePromos() {
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
