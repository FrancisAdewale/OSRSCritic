package com.example.osrscritic.di

import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val googleLoginViewModelModule = module {

    viewModel {
        GoogleLoginViewModel(get())
    }
}


