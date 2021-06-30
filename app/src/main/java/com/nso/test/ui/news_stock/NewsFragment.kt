package com.nso.test.ui.news_stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nso.test.App
import com.nso.test.R
import com.nso.test.databinding.FragmentNewsBinding
import com.nso.test.di.component.AppComponent
import com.nso.test.di.factory.SharedViewModelFactory
import com.nso.test.ui.SharedViewModel
import com.nso.test.ui.news_stock.adapter.NewsAdapter
import com.nso.test.utils.*

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding?.root
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
        val viewModel: SharedViewModel by viewModels {
            val storageRepository = dependencyFactory.provideStorageRepository()
            val remoteRepository = dependencyFactory.provideRemoteRepository()
            SharedViewModelFactory(storageRepository, remoteRepository)
        }
        this.viewModel = viewModel
    }

    private fun initRecyclerView() {
        val adapter = NewsAdapter { news ->
            val bundle = Bundle()
            bundle.putString(URL_BUNDLE, news.url)
            findNavController().navigate(R.id.newsWebViewFragment, bundle)
        }
        binding.news.adapter = adapter
        viewModel.getNews(
            arguments?.getString(NEWS_BUNDLE).toString(),
            from = yesterday(requireContext()),
            to = today(requireContext())
        ) {
            adapter.submitList(it)
            binding.progress.hide()
            if (it.isEmpty()) {
                binding.notFindNews.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}