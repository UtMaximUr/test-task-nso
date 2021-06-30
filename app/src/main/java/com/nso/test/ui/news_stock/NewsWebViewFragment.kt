package com.nso.test.ui.news_stock

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nso.test.App
import com.nso.test.databinding.FragmentWebViewBinding
import com.nso.test.di.component.AppComponent
import com.nso.test.di.factory.SharedViewModelFactory
import com.nso.test.ui.SharedViewModel
import com.nso.test.utils.*

class NewsWebViewFragment : Fragment() {

    private var binding: FragmentWebViewBinding? = null

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initViewModel()
        setListener()
        initWebView()
        setUrl()
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

    private fun setListener() {
        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        binding?.webView?.webViewClient = object : NewsWebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding?.progress?.hide()
            }
        }
        binding?.webView?.settings?.javaScriptEnabled = true
    }

    private fun setUrl() {
        val currentUrl = arguments?.getString(URL_BUNDLE)
        binding?.webView?.loadUrl(currentUrl.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}