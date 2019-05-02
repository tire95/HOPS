# Arkkitehtuuri

## Rakenne

Alla oleva kuva esittää ohjelman pakkausrakennetta.

![pakkausrakenne](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/pakkausrakenne.png)

Pakkaus *ui* sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, *domain* sisältää sovelluslogiikan, *dao* tietojen pysyväistallennuksesta vastaavan koodin, ja *database* tietokantaan yhdistämisestä vastaavan koodin.

## Käyttöliittymä

Käyttöliittymässä on kuusi eri näkymää:

- sisäänkirjautuminen
- uuden käyttäjän luominen
- kurssien listaus
- uuden kurssin luominen
- admin-näkymä

Jokainen näkymistä on toteutettu Scene-oliona, jotka ovat yksi kerrallaan näkyvänä. Käyttöliittymä on eristetty sovelluslogiikasta.

Kun kurssilista muuttuu, eli kun käyttäjä kirjautuu sisään tai lisää itselleen uuden kurssisuorituksen, kutsutaan metodia *getCourses*, joka päivittää kurssilistan.

Admin-näkymään kirjautuminen vaatii salasanan, joka voidaan vaihtaa juurikansiossa olevasta *config.properties*-tiedostosta. Kun admin-näkymässä poistetaan opiskelija, kutsutaan metodia *getStudents*, joka päivittää opiskelijalistan.

## Sovelluslogiikka

Sovelluslogiikasta vastaa luokka *HOPSService*, joka tarjoaa käyttöliittymän toiminnoille sopivat metodit. Alla oleva luokkakaavio kuvaa ohjelman eri osien suhdetta toisiinsa.

![luokkakaavio](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/luokkakaavio.png)

## Tietojen pysyväistallennus

Opiskelijat ja kurssisuoritukset tallennetaan SQL-tietokantaan. Tästä huolehtii pakkauksen *dao* luokat *SQLStudentDao* ja *SQLCourseDao*.

Alla oleva tietokantakaavio kuvaa tietokantataulujen suhteen toisiinsa.

![tietokantakaavio](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/tietokantakaavio.png)

Luokat noudattavat Data Access Object -suunnittelumallia, ja tarvittaessa ne voidaan korvata uusilla toteutuksilla, jos tietoja halutaan tallentaa muilla tavoilla.

SQL-tietokannan nimen voi vaihtaa tiedostosta *config.properties*, joka sijaitsee juurikansiossa.

## Sovelluksen konfigurointi

Tiedoston *config.properties* avulla voidaan konfiguroida sovellusta. Tiedoston rakenne on seuraavanlainen: 

	database=HOPSDatabase.db
	coursePointsMax=180
	adminPassword=salasana1234

Muuttamalla kohdan *database* parametria voidaan valita, minkä nimistä SQL-tietokantaa sovellus käyttää. Kohta *coursePointsMax* määrittää sovelluksessa näkyvän opintopisteiden ylärajan. *adminPassword* määrittää admin-salasanan, jolla voidaan kirjautua admin-näkymään. 

## Sekvenssikaaviot

### Kirjautuminen

Alla oleva sekvenssikaavio kuvaa tilannetta, kun käyttäjä kirjautuu järjestelmään

![logIn](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/logInSequence.png)

Kun käyttäjä painaa sisäänkirjautumisnappia, tapahtumankäsittelijä kutsuu *HOPSService*:n login-metodia parametrina käyttäjän antama käyttäjätunnus. *HOPSService* määrittää *studentDao*:n kautta, löytyykö kyseisellä käyttäjätunnuksella opiskelijaa. Jos opiskelija löytyy, *studentDao* palauttaa kyseisen opiskelijan, *HOPSService* asettaa kyseisen opiskelijan sisäänkirjautuneeksi ja palauttaa *true* *HOPSUi*:lle. Tämän jälkeen *HOPSUi* kutsuu omaa metodiaan *getCourses*, jossa *HOPSUi* kutsuu *HOPSService*:n *getAllCourses*-metodia. Sisäänkirjautuneen opiskelijan id haetaan, ja tämän perusteella etsitään opiskelijan kaikki kurssit *courseDao*:sta metodilla *findAllForStudent(id)*. Tämä palauttaa listan *courses* *HOPSService*:lle, joka edelleen palauttaa listan *HOPSUi*:lle, joka tämän perusteella päivittää kurssilistan näkymään. Tämän jälkeen *HOPSUi* vaihtaa näkymän *loggedInScene*:n.

### Uuden opiskelijan luonti

Alla oleva sekvenssikaavio kuvaa tilannetta, kun käyttäjä luo uuden opiskelijan järjestelmään

![createStudent](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/createNewStudentSequence.png)

Kun käyttäjä painaa *newUserButton*:ia, tapahtumankäsittelijä vaihtaa ui:n uuden opiskelijan luontia varten tehtyyn *Scene*:n. Kun tämän jälkeen painaa *createUserButton*:a, *HOPSUi* kutsuu *HOPSService*:n *createNewUser*-metodia, jolle annetaan käyttäjän syöttämät nimi ja käyttäjätunnus parametreina. *HOPSService* luo uuden opiskelijan annetuilla parametreilla ja asettaa tämän id:n tilapäisesti arvoon -1. Tämän jälkeen *HOPSService* kutsuu *studentDao*:n metodia *save(student)* antaen parametrina juuri luodun opiskelijan. *studentDao* aluksi tarkistaa metodilla *findByUsername*, että annetulla käyttäjätunnuksella ei löydy opiskelijaa tietokannasta, ja palauttaa itselleen arvon *null* jos näin on. Tämän jälkeen *studentDao* tallentaa opiskelijan tietokantaan; tässä vaiheessa opiskelijan id määräytyy tietokannassa olevien opiskelijoiden määrän mukaan. Kun opiskelija on luotu, *studentDao* kutsuu metodiaan *findByUsername*, joka palauttaa juuri luodun opiskelijan. Tämä palautetaan edelleen *HOPSService*:lle, joka taas palauttaa *true* *HOPSUi*:lle. *HOPSUi* asettaa tämän jälkeen näkymän sisäänkirjautumisruutuun.
