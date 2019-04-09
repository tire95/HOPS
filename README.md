# Ohjelmistotekniikka, harjoitustyö - HOPS

Sovelluksen avulla opiskelija pystyy seuraamaan opintojensa edistymistä.

Tällä hetkellä sovellus tukee vain uusien opiskelijoiden lisäämistä ja olemassa olevien opiskelijoiden listaamista.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/tire95/HOPS/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/tire95/HOPS/blob/master/dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/tire95/HOPS/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

	mvn test

Testikattavuus luodaan komennolla

	mvn jacoco:report

### Suoritus komentoriviltä

Ohjelman suoritus komentoriviltä onnistuu komennolla

	mvn compile exec:java -Dexec.mainClass=ui.HOPSUi

