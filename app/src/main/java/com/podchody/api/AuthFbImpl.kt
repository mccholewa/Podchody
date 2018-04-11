package com.podchody.api

/**
 * Created by Misiu on 10.04.2018.
 */

import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.podchody.vo.StringLanguageResource
import timber.log.Timber
import java.util.*
import kotlin.concurrent.timerTask


class AuthFbImpl(private val auth: FirebaseAuth) : AuthFb {


    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signin(email: String?, password: String?): String? {
        auth.signOut()

        val language = Locale.getDefault().getLanguage()
        val languageResource: StringLanguageResource = StringLanguageResource(language)
        //if (Locale.getDefault().language.equals( Locale("en").language))
        if (auth.currentUser == null) {
            if (email.isNullOrBlank())
                return languageResource.enterEmailAddress
            else if (password.isNullOrBlank())
                return languageResource.enterPassword
            else {
                auth.signInWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                                Timber.d("Sing in successful")
                            } else {
                                Timber.d("Task exception %s",
                                        task.exception.toString().split(':')[1])
                           }
                        }
            }
        }
        return languageResource.youAreSingedIn
    }

    fun heandleAuthenticationException(exception: Exception)
    {
        exception
    }



    fun translateSinginErrorToPolish(error: String):String{
        val language = Locale.getDefault().getDisplayLanguage()
        // val language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration())
        if(language == "pl") {
            when (error) {
                "auth/invalid-email" -> return "Nieprawidłowy adress email"
                "auth/user-disabled" -> return "Użytkownik zablokowany"
                "auth/user-not-found" -> return "Nie znaleziono takiego użytkownika"
                "auth/wrong-password" -> return "Nie prawidłow hasło"
                "auth/expired-action-code" -> return "Link stracił ważność"
            }
            return "Wystąpił błąd"
        }
        else return ""
//        if(language == "en")
//            return error
    }
}