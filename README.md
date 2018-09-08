# Local Database

https://introsdelocalstoragegrossele.herokuapp.com/ws/people?wsdl

## Deploy in Heroku 
>heroku login<br />
>heroku create --stack cedar-14 --buildpack https://github.com/IntroSDE/heroku-buildpack-ant.git<br />
>git push heroku master<br />
>heroku open<br />
>(Rename app)<br />
>git remote rm heroku<br />
>heroku git:remote -a introsdelocalstoragegrossele

## Tables
### User
#### Requests
##### GetUserByToken(token) -> Usata per query esiste utente
##### GetUser(token) -> Nome, cognome, last height, last weight
##### NewUser(token) -> 
##### SetUserName(token, name) ->
##### SetUserSurname(token, surname) ->

### Place
##### getLast10Places(token, type)
##### getLastPlace(token, type)
##### updatePlace(token, type, value) -> add Place ricordare data

### PlaceType
##### getTypes()

### Suggestion
##### getSuggestions(token) -> n Suggestion quanti i tipi
##### setSuggestion(token, type, Suggestiontype, value) -> overwrite the previous Suggestion of that type
##### getSuggestion(token, type)

