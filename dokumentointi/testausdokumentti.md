# Testausdokumentti

## Yksikkö- ja integraatiotestaus

Automatisoiduissa testeissä käytetään *testDatabase.db*-nimistä tietokantaa, joka alustetaan joka testin alussa.

Luokille *Student* ja *Course* on luotu yksikkötestejä, ja luokille *HOPSService*, *SQLCourseDao* ja *SQLStudentDao* on luotu integraatiotestejä. *HOPSServiceTest* simuloi *HOPSService*:n toimintoja, ja *SQLStudentDaoTest* ja *SQLCourseDaoTest* simuloivat *SQLStudentDao*:n ja *SQLCourseDao*:n toimintoja. Käyttöliittymä *HOPSUi* on jätetty testaamatta.

### Testauskattavuus

Käyttöliittymää lukuunottamatta testausten rivi- ja haarautumakattavuus ovat kummatkin 100%.

![testikattavuus](https://github.com/tire95/HOPS/blob/master/dokumentointi/kuvat/testikattavuus.png)

## Järjestelmätestaus

Sovelluksen toimintaa on testattu Linux- ja Windows 10 -järjestelmissä, jotka oli varustettu Javan 8:lla versiolla, käyttöohjeen mukaisesti, kun käynnistyshakemistossa on *config.properties*-tiedosto.

Sovelluksen toimintaa on testattu, kun tietojen tallennusta varten oleva tietokanta on valmiiksi olemassa, sekä kun tietokantaa ei ole, jolloin se luodaan automaattisesti.

Määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on testattu sekä oikeanlaisilla että virheellisillä syötteillä. Virheellisten syötteiden tapauksessa ohjelma ilmoittaa tästä käyttäjälle eikä kaadu.

## Sovellukseen jääneet ongelmat

- *config.properties*-tiedoston puuttuessa sovellus ei käynnisty ja tulostaa virheen komentoriville
