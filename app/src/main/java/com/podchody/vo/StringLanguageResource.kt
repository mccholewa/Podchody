package com.podchody.vo

/**
 * Created by Misiu on 11.04.2018.
 */



class StringLanguageResource(language: String?){
    var enterEmailAddress: String = ""
    var enterPassword: String = ""
    var youAreSingedIn: String = ""
    var unknownError: String = ""
    var signInFail: String = ""
    var noNetwork: String = ""
    var noGameTitle: String =""
    var waitingForPlayer:String?=""
    var needSecondPlayer:String?=""
    var connectionErorr:String?=""

    init {
        if(language == "en" || language == "English" ) {
            enterEmailAddress = "Enter email address"
            enterPassword = "Enter password"
            youAreSingedIn = "You are already signed in"
            unknownError = "Unknown error"
            signInFail = "Login failed"
            noNetwork = "Connection problem"
            noGameTitle = "Game needs title"
            waitingForPlayer = "Waiting  for player"
            needSecondPlayer = "You need a second player"
            connectionErorr = "Connection error"
        }
        else if(language == "pl" || language == "polski"){
            enterEmailAddress = "Wprowadź adres email"
            enterPassword = "Wprowadź hasło"
            youAreSingedIn = "Jesteś już zalogowany"
            unknownError = "Nieznany błąd"
            signInFail = "Logowanie nie powiodło się"
            noNetwork = "Porblem z połączeniem"
            noGameTitle = "Ustaw tytuł gry"
            waitingForPlayer = "Oczekiwanie na gracza"
            needSecondPlayer = "Potrzebujesz drugiego gracza"
            connectionErorr = "Problem z połączeniem"
        }
    }
}
