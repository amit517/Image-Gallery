package com.assessment.mobileengineerassesment

import androidx.navigation.fragment.NavHostFragment
import com.assessment.base.view.BaseActivity
import com.assessment.mobileengineerassesment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int = R.layout.activity_main

    override fun initOnCreateView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dummy_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}
