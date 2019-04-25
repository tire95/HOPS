# Ohjelmistotekniikka, harjoitustyö - HOPS

Sovelluksen avulla opiskelija pystyy seuraamaan opintojensa edistymistä.

Sovelluksen käynnistyessä käyttäjä voi kirjautua sisään käyttäjätunnuksella, luoda uuden käyttäjän, tai siirtyä admin-tilaan admin-salasanalla.

Kirjauduttuaan sisään käyttäjä näkee kurssisuorituksensa ja voi lisätä itselleen kurssisuorituksia.

Admin-tilassa tietokannasta voi poistaa opiskelijoita; kun opiskelija poistetaan järjestelmästä, tähän liittyvät kurssisuoritukset poistetaan samalla.

Sovelluksen käyttämän tietokannan nimen, maksimiopintopisteiden määrän, ja admin-salasanan voi vaihtaa *config.properties*-tiedostosta, joka sijaitsee juurikansiossa.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/tire95/HOPS/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/tire95/HOPS/blob/master/dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/tire95/HOPS/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/tire95/HOPS/blob/master/dokumentointi/kayttoohje.md)

## Releaset

[Viikko 5 (pre-release)](https://github.com/tire95/HOPS/releases/tag/viikko5)

[Viiko 6 (pre-release)](https://github.com/tire95/HOPS/releases/tag/viikko6)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

	mvn test

Testikattavuus luodaan komennolla

	mvn jacoco:report

Kattavuusraporttia voi tarkastella tiedostosta *target/site/jacoco/index.html*

### Suoritus komentoriviltä

Ohjelman suoritus komentoriviltä onnistuu komennolla

	mvn compile exec:java -Dexec.mainClass=ui.HOPSUi

### Suoritettavan jarin luonti

Suoritettava jar voidaan luoda komennolla

	mvn package

Luotu jar-tiedosto, *HOPS-1.0-SNAPSHOT.jar*, löytyy kansiosta *target*

### JavaDoc

JavaDoc generoidaan komennolla

	mvn javadoc:javadoc

### Checkstyle

Checktylen tarkistukset suoritetaan komennolla

	mvn jxr:jxr checkstyle:checkstyle

Checkstylen voi tarkastella tiedostosta *target/site/checkstyle.html*
