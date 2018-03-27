package com.podchody.ui.login

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import com.podchody.ui.common.getParam

@Module
abstract class LoginModule {
    abstract fun bindLoginFragment(): LoginFragment
}
