package com.nso.test.ui.stock_list.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nso.test.App
import com.nso.test.R
import com.nso.test.databinding.FragmentStockListBinding
import com.nso.test.di.component.AppComponent
import com.nso.test.di.factory.SharedViewModelFactory
import com.nso.test.ui.SharedViewModel
import com.nso.test.ui.stock_list.stock_adapter.StockAdapter
import com.nso.test.utils.STOCK_BUNDLE
import com.nso.test.utils.hide

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentStockListBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var adapter: StockAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initViewModel()
        initRecyclerView()
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
        adapter = StockAdapter({ stock ->
            val bundle = Bundle()
            bundle.putSerializable(STOCK_BUNDLE, stock)
            findNavController().navigate(R.id.stockFragment, bundle)
        }, { favouriteStock ->
            viewModel.updateStock(favouriteStock)
        })
        binding.stockList.adapter = adapter
        adapter.submitList(viewModel.getFavoriteList())
        binding.progress.hide()
    }
}