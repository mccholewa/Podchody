package com.podchody.di

/**
 * Created by Misiu on 08.01.2018.
 */

import android.app.Application
import com.podchody.ViewLibModule
import dagger.BindsInstance
import dagger.Component
import com.podchody.NavigationController
import com.podchody.PodchodyApp
import com.podchody.ui.login.LoginModule
import com.podchody.ui.register.RegisterModule
import javax.inject.Singleton
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule





@Singleton
@Component(modules = [
    ViewLibModule::class,
    AppModule::class,
    AndroidInjectorActivityBindingModule::class,
    AndroidSupportInjectionModule::class,
    LoginModule::class,
    RegisterModule::class
])
interface AppComponent : AndroidInjector<PodchodyApp> {

    val navigationController: NavigationController

    @Component.Builder interface Builder {
        @BindsInstance fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

//@Singleton
//@Component(modules = [ViewLibModule::class])
//interface AppComponent {
//
//    val loginViewModel: LoginViewModel
//
//    val registerViewModel: RegisterViewModel
//
//    val navigationController: NavigationController
//
//    @Component.Builder interface Builder {
//        @BindsInstance fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }
//}