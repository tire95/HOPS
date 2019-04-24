# Käyttöohje

Lataa tiedosto [hopsapp.jar]()

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistosta löytyy konfiguraatiotiedosto *config.properties*, jolla voidaan määritellä käytettävän tietokannan nimi, opintopisteiden maksimimäärän, ja admin-salasanan. Tiedosto näyttää seuraavanlaiselta:

	database=HOPSDatabase.db
	coursePointsMax=180
	adminPassword=salasana1234


## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla


	java -jar hopsapp.jar

Ohjelman käynnistyessä se luo käynnistyshakemistoon tietokannan, jonka nimi on määritelty *config.properties*-tiedostossa.
