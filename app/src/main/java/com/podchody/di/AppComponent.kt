package com.podchody.di

/**
 * Created by Misiu on 08.01.2018.
 */

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import com.podchody.NavigationController
import com.podchody.ui.login.LoginViewModel
import com.podchody.ui.newuser.NewuserViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val loginViewModel: LoginViewModel

    val newuserViewModel: NewuserViewModel

    val navigationController: NavigationController

    @Component.Builder interface Builder {
        @BindsInstance fun application(application: Application): Builder

        fun build(): AppComponent
    }
}