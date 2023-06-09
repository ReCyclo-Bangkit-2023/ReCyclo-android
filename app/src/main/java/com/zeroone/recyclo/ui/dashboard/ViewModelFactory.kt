package com.zeroone.recyclo.ui.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.detail.DetailActivity
import com.zeroone.recyclo.ui.longlist.Injection


class ViewModelFactory  private constructor(private val mApplication: Application, private val sessionPreference: SessionPreference) : ViewModelProvider.NewInstanceFactory() {
    companion object{

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application,sessionPreference: SessionPreference): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application,sessionPreference)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)){
            return mApplication?.let { DashboardViewModel(Injection.provideRepository(mApplication,sessionPreference),sessionPreference)} as T
        } else if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return mApplication?.let { DashboardViewModel(Injection.provideRepository(mApplication,sessionPreference),sessionPreference) } as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}