package com.nso.test.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.nso.test.App
import com.nso.test.databinding.FragmentStockBinding
import com.nso.test.di.component.AppComponent
import com.nso.test.di.factory.SharedViewModelFactory
import com.nso.test.domain.entity.StockEntity
import com.nso.test.ui.SharedViewModel
import com.nso.test.ui.news_stock.NewsFragment
import com.nso.test.ui.stock.adapter.StockViewPagerAdapter
import com.nso.test.utils.NEWS_BUNDLE
import com.nso.test.utils.STOCK_BUNDLE
import com.nso.test.utils.setImagePath

class StockFragment : Fragment() {

    private var _binding: FragmentStockBinding? = null
    private val binding get() = _binding!!

    private var currentStock: StockEntity? = null
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        injectDagger()
        initViewModel()
        return _binding?.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStock()
        setListener()
        initViewPager()
    }

    private fun setStock() {
        currentStock = arguments?.getSerializable(STOCK_BUNDLE) as StockEntity?
        currentStock?.let { stock ->
            viewModel.setStock(stock)
            binding.lifecycleOwner = this
            binding.stockViewModel = viewModel
            binding.logo.setImagePath(stock.logo)
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = StockViewPagerAdapter(this).apply {
            val news = NewsFragment()
            val bundle = Bundle()
            bundle.putString(NEWS_BUNDLE, currentStock?.ticker)
            news.arguments = bundle
            add(news)
        }
        TabLayoutMediator(binding.dots, binding.viewPager) { _, _ ->
        }.attach()
    }

    private fun setListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}