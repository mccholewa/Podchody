package com.podchody.ui.login

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment
}
