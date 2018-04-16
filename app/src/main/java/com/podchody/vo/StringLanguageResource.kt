package com.podchody.vo

/**
 * Created by Misiu on 11.04.2018.
 */



class StringLanguageResource(language: String){
    var enterEmailAddress: String = ""
    var enterPassword: String = ""
    var youAreSingedIn: String = ""
    var unknownError: String = ""
    var signInFail: String = ""
    var noNetwork: String = ""

    init {
        if(language == "en") {
            enterEmailAddress = "Enter email address"
            enterPassword = "Enter password"
            youAreSingedIn = "You are already signed in"
            unknownError = "Unknown error"
            signInFail = "Login failed"
            noNetwork = "Connection problem"
        }
        else if(language == "pl"){
            enterEmailAddress = "Wprowadź adres email"
            enterPassword = "Wprowadź hasło"
            youAreSingedIn = "Jesteś już zalogowany"
            unknownError = "Nieznany błąd"
            signInFail = "Logowanie nie powiodło się"
            noNetwork = "Porblem z połączeniem"
        }
    }
}
