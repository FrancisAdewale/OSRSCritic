package com.example.osrscritic.di

import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.repo.FirebaseRepo
import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import com.example.osrscritic.viewmodel.OtherLoginViewModel
import com.example.osrscritic.viewmodel.ProfileDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val googleLoginViewModelModule = module {

    viewModel {
        GoogleLoginViewModel(get(), get())
    }
}

val otherLoginViewModelModule = module {
    viewModel {
        OtherLoginViewModel(get(), get())
    }
}

val profileDetailsViewModel = module {
    viewModel {
        ProfileDetailsViewModel(get())
    }
}

val authModule = module {
    single { AuthRepo() }
}

val firebaseDBModule = module {
    single{ FirebaseRepo() }
}


