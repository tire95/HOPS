# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla opiskelija pystyy seuraamaan omaa edistymistään opinnoissaan. Luotuaan käyttäjätunnuksen opiskelija voi kirjata suoritettuja kurssejaan järjestelmään, ja tätä kautta seurata omien opintojen edistymistä.

## Toimintaympäristön rajoitteet

- Sovellus toimii Linux-järjestelmillä
- Sovellus tallentaa tiedot paikallisen koneen levylle

## Käyttäjät

Sovelluksella on ainostaan yksi käyttäjärooli; opiskelija. Erillinen admin-näkymä on sovelluksessa, mutta tähän pääsy ei vaadi käyttäjätunnusta, vain admin-salasanan.

## Toiminnot

Ennen kirjautumista:

- käyttäjä voi luoda itselleen uuden käyttäjätunnuksen
	- käyttäjätunnuksen täytyy olla uniikki, sekä nimen että käyttäjätunnuksen täytyy olla vähintään 3 merkkiä pitkä
- käyttäjä voi kirjautua omalla käyttäjätunnuksellaan
	- jos käyttäjätunnusta ei löydy, sovellus ilmoittaa tästä
- käyttäjä voi siirtyä admin-näkymään admin-salasanalla
	- jos salasana on väärin, sovellus ilmoittaa tästä

Kirjauduttuaan:

- käyttäjä näkee kurssit, jotka hän on suorittanut, sekä näihin liittyvät opintopisteet
- käyttäjä näkee opintopisteittensä summan ja tutkinnon vaatiman opintopistemäärän
- käyttäjä voi lisätä uuden kurssisuorituksen järjestelmään
	- kurssikoodin ja kurssinimen täytyy olla yksittäiselle opiskelijalle uniikit
- käyttäjä voi kirjautua ulos järjestelmästä

Admin-näkymässä:

- käyttäjä voi poistaa tietokannasta opiskelijan ja tähän liittyvät kurssisuoritukset

Mahdollisia jatkokehitysideoita:

- käyttäjä voi suunnitella tulevia opintojaan lisäämällä kursseja listaan, ja suoritettuaan kurssin opiskelija voi merkata tämän suoritetuksi
- käyttäjien yhteyteen salasana, joka vaaditaan sisäänkirjautumiseen
- kurssisuoritusten editointi

