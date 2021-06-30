package com.nso.test.ui.stock_list.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nso.test.ui.stock_list.stock_adapter.StockAdapter
import com.nso.test.App
import com.nso.test.R
import com.nso.test.databinding.FragmentStockListBinding
import com.nso.test.di.component.AppComponent
import com.nso.test.di.factory.SharedViewModelFactory
import com.nso.test.ui.SharedViewModel
import com.nso.test.ui.stock_list.stock_adapter.PaginationLoadingDecoration
import com.nso.test.ui.stock_list.stock_adapter.PaginationLoadingDecoration.*
import com.nso.test.utils.STOCK_BUNDLE
import com.nso.test.utils.hide

class CommonFragment : Fragment() {

    private lateinit var binding: FragmentStockListBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var adapter: StockAdapter
    private lateinit var loadingDecoration: PaginationLoadingDecoration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockListBinding.inflate(layoutInflater)
        injectDagger()
        initViewModel()
        initRecyclerView()
        return binding.root
    }

    private fun injectDagger() {
        App.instance.appComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AppComponent =
            (requireActivity().application as App).appComponent
        val storageRepository = dependencyFactory.provideStorageRepository()
        val remoteRepository = dependencyFactory.provideRemoteRepository()
        val viewModel: SharedViewModel by viewModels {
            SharedViewModelFactory(storageRepository, remoteRepository)
        }
        this.viewModel = viewModel
    }

    private fun initRecyclerView() {
        loadingDecoration = PaginationLoadingDecoration()
        adapter = StockAdapter({ stock ->
            val bundle = Bundle()
            bundle.putSerializable(STOCK_BUNDLE, stock)
            findNavController().navigate(R.id.stockFragment, bundle)
        }, { favouriteStock ->
            viewModel.updateStock(favouriteStock)
        })
        binding.stockList.addItemDecoration(loadingDecoration)
        binding.stockList.adapter = adapter
        viewModel.getStockList {
            adapter.submitList(it)
            if (it.isNotEmpty())
                binding.progress.hide()
        }
        loadingDecoration.setIsLastItemListener(object : IsLastItemListener {
            override fun isLastItem() {
//                loadingDecoration.isEnabled(false)
//                viewModel.getRemoteStockList {
//                    adapter.submitList(it)
//                    loadingDecoration.isEnabled(true)
//                }
            }
        })
    }
}