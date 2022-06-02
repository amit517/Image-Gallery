package com.assessment.mobileengineerassesment.base

import com.assessment.base.view.BaseActivity
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int = R.layout.activity_main

    override fun initOnCreateView() {

    }
}
