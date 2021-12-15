package com.example.osrscritic.di

import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.repo.FirebaseRepo
import com.example.osrscritic.viewmodel.*
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

val accountDetailsViewModel = module {
    viewModel {
        AccountFragmentViewModel(get())
    }
}

val mapsFragmentViewModel = module {
    viewModel {
        MapsFragmentViewModel(get())
    }
}

val authModule = module {
    single { AuthRepo() }
}

val firebaseModule = module {
    single{ FirebaseRepo() }
}


