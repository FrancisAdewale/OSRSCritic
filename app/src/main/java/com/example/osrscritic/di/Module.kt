package com.example.osrscritic.di

import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import com.example.osrscritic.viewmodel.OtherLoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val googleLoginViewModelModule = module {

    viewModel {
        GoogleLoginViewModel(get())
    }
}

val otherLoginViewModelModule = module {
    viewModel {
        OtherLoginViewModel(get())
    }
}

val authModule = module {
    single { AuthRepo() }
}


