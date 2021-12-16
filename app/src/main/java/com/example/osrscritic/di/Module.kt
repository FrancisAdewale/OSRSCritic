package com.example.osrscritic.di

import com.example.osrscritic.network.RetrofitService
import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.repo.DisplayUserRepo
import com.example.osrscritic.repo.FirebaseRepo
import com.example.osrscritic.viewmodel.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.riningan.retrofit2.converter.csv.CsvConverterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

val displayUserRepoModule = module {
    single { DisplayUserRepo(get())}
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit) = retrofit.create(RetrofitService::class.java)
    single {
        provideUserApi(get())
    }
}

val displayUserModule = module {
    single {
        DisplayUserViewModel(get(),get())
    }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient() = OkHttpClient.Builder().build()

    fun provideRetrofit(gson: Gson ,client: OkHttpClient) =

        Retrofit.Builder()
            .baseUrl("https://apps.runescape.com/runemetrics/profile/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }

}


