package com.renj.mvvm

import com.renj.mvvm.databinding.ActivityMainBinding
import com.renj.mvvmbase.view.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun createAndBindViewModel(viewDataBinding: ActivityMainBinding?): MainViewModel {
        var mainViewModel = MainViewModel()
        viewDataBinding?.mainViewModel = mainViewModel
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
