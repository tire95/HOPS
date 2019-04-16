# Arkkitehtuuri

## Sovelluslogiikka

Alla oleva luokkakaavio kuvaa ohjelman sovelluslogiikkaa.

![luokkakaavio](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/luokkakaavio.png)

## Sekvenssikaaviot

### Kirjautuminen

Alla oleva sekvenssikaavio kuvaa tilannetta, kun käyttäjä kirjautuu järjestelmään

![logIn](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/logInSequence.png)

Kun käyttäjä painaa sisäänkirjautumisnappia, tapahtumankäsittelijä kutsuu HOPSService:n login-metodia parametrina käyttäjän antama käyttäjätunnus. HOPSService määrittää studentDao:n kautta, löytyykö kyseisellä käyttäjätunnuksella opiskelijaa. Jos opiskelija löytyy, studentDao palauttaa kyseisen opiskelijan, HOPSService asettaa kyseisen opiskelijan sisäänkirjautuneeksi, palauttaa *true* HOPSUi:lle. Tämän jälkeen HOPSUi kutsuu omaa metodiaan *getCourses()*, jossa HOPSUi kutsuu HOPSService:n *getAllCourses()*-metodia. Sisäänkirjautuneen opiskelijan id haetaan, ja tämän perusteella etsitään opiskelijan kaikki kurssit courseDao:sta metodilla *findAllForStudent(id)*. Tämä palauttaa listan *courses* HOPSService:lle, joka edelleen palauttaa listan HOPSUi:lle, joka tämän perusteella päivittää kurssilistan näkymään. Tämän jälkeen HOPSUi vaihtaa näkymän *loggedInScene*:n.

### Uuden opiskelijan luonti

Alla oleva sekvenssikaavio kuvaa tilannetta, kun käyttäjä luo uuden opiskelijan järjestelmään

![createStudent](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/createNewStudentSequence.png)
