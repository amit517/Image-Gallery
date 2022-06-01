package com.assessment.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.assessment.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    protected val bindingView: VB
        get() = viewDataBinding
    private lateinit var viewDataBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layoutId = layoutId()
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        initOnCreateView()

        val viewModel = getViewModel()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel?.showMessage?.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        return viewDataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding.unbind()
    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun getViewModel(): BaseViewModel?

    protected abstract fun initOnCreateView()
}
