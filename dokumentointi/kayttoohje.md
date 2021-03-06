# Käyttöohje

Lataa tiedostot [hops-app.jar ja config.properties](https://github.com/tire95/HOPS/releases/tag/viikko7)

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistosta löytyy konfiguraatiotiedosto *config.properties*, jolla voidaan määritellä käytettävän tietokannan nimi, opintopisteiden maksimimäärän, ja admin-salasanan. Tiedosto näyttää seuraavanlaiselta:

	database=HOPSDatabase.db
	coursePointsMax=180
	adminPassword=salasana1234

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

	java -jar hopsapp.jar

Ohjelman käynnistyessä se luo käynnistyshakemistoon tietokannan, jonka nimi on määritelty *config.properties*-tiedostossa.

Ohjelma käynnistyy alla olevaan ruutuun.

![logInScreen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/logInScreen.png)

## Uuden käyttäjän/opiskelijan luonti

Sisäänkirjautumisruudussa paina *Luo uusi käyttäjä* -nappia. Ohjelman näkymä siirtyy alla olevaan näkymään.

![newUserScreen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/newUserScreen.png)

Uuden käyttäjän luontia varten kirjoita nimi ja käyttäjätunnus syötekenttiin. Käyttäjätunnus täytyy olla uniikki. Paina lopuksi *Luo uusi käyttäjä* -nappia.

## Sisäänkirjautuminen

Kun uusi käyttäjätili on luotu tai haluat kirjautua olemassa olevalla tunnuksella, kirjoita käyttäjätunnuksesi syötekenttään alla olevan kuvan mukaan. Lopuksi paina *Kirjaudu*-nappia; jos käyttäjätunnus löytyy tietokannasta, sisäänkirjautuminen onnistuu ja näkymä siirtyy kurssilistausnäkymään.

![logInScreen2](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/logInScreen2.png)

## Kurssilistausnäkymä

Kirjauduttuasi sisään näkymä on seuraavanlainen:

![courseScreen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/courseScreen.png)

## Uusien kurssisuoritusten luonti ja kurssisuoritusten poisto

Kirjauduttuasi sisään paina *Luo uusi kurssi* -nappia. Näkymä siirtyy kurssinluontinäkymään. Kirjoita syötekenttiin kurssikoodi, kurssin nimi ja opintopisteet. Kurssikoodin ja nimen pitää olla uniikkeja, ja opintopisteiden pitää olla kokonaisluku.

![newCourseScreen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/newCourseScreen.png)

Paina *Luo uusi kurssi* -nappia. Näkymä siirtyy kurssilistausnäkymään.

![courseScreen2](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/courseScreen2.png)

Jos haluat poistaa kurssisuorituksen, paina kurssisuorituksen vieressä olevaa *Poista kurssi* -nappia.

## Admin-näkymä

Admin-näkymää varten kirjoita admin-salasana sisäänkirjautumisruudussa.

![toAdminScreen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/toAdminScreen.png)

Admin-näkymä näyttää seuraavanlaiselta:

![adminSceen](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/adminScene.png)

Admin-näkymässä voit poistaa opiskelijan ja tähän liittyvät kurssisuoritukset painamalla opiskelijan käyttäjätunnuksen viereistä *Poista opiskelija* -nappia.
