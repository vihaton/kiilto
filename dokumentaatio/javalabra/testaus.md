#Testaus

Tässä dokumentissa selvitetään, miten ohjelmaa on testattu lähtien metoditasosta päätyen sovellustasoon.

##JUnit-testit

Sovelluksen pelilogiikalle on kirjoitettu 60kpl JUnit-testejä, joiden rivikattavuus on kirjoittamishetkellä 91%. Rivikattavuuden kasvattamista haittaavat pitkät toString -metodit (tekstikäyttöliittymäversiota varten) sekä Pelivelho-luokan riippuvuus käyttöliittymäelementeistä.

Logiikan testauksella saavutetaan kuitenkin hyvä käyttövarmuus: kaikki pelin säännöissä määritellyt erityistapaukset on testattu toimiviksi logiikan testeissä. Lisäksi useiden erityis- ja rajatapauksien toimivuus on pystytty tarkastamaan pelilogiikan testeissä.

##Sovellustason testaus

Pelin .jar paketin toimivuus on tarkastettu NetBeansissä, komentoriviltä sekä "klikkaamalla" sekä unix että windows käyttöjärjestelmällä pyörivissä tietokoneissa.

Peli itsessään on pelattu läpi muutaman kerran, samalla testaten toimiviksi:

###Alkuvalikon toiminta

  * Jokaisesta napista tapahtuu, mitä on tarkoituskin
  * kaikki tekstit näkyvät odotetusti

###Kaikkien pelin elementtien piirtotoiminnot
  * Kaikki elementit piirtyvät selkeästi kaikissa pelitilanteissa
  * HUOM. piirtotoiminnot on suunniteltu fuksiläppärin näytön resoluutiolle olettaen, että pienenpää näyttöä / huononpaa resoluutiota ei tulla missään käyttämään. Piirto-ominaisuudet eivät skaalaudu mitenkään näytön resoluution muuttuessa.
  * Jokainen toiminto välittyy näytölle välittömästi toiminnon jälkeen: kun pelaaja nostaa nallekarkkeja, ostaa omistuksen/varauksen, varaa omistuksen tai vaakuuttaa merkkihenkilön muutos välittyy ruudulle oikein välittömästi.

###Kaikkien käyttöliittymäelementtien toiminta

  * napit, joilla pelaaja valitsee mitä tekee vuorollaan toimivat odotetusti
  * "takaisin" -nappi, joka palauttaa pelaajan valitsemaan mitä tekee vuorollaan
  * nallekarkkien valitsemiseen käytettävät laskurit toimivat odotetusti, ja niissä olevat rajoitukset toimivat. Jos pelaaja yrittää nostaa laittoman määrän nallekarkkeja, sovellus herjaa, laskurit nollaantuvat eikä mitään odottamatonta tapahdu.
  * ">" -nappi toimii odotetusti, lähettää Pelivelholle pelaajan valitsemat määrät oikeanlaisina
  * omaisuuksien ostamiseen ja varaamiseen sekä varauksien ostamiseen käytettävät valitsinnapit toimivat odotetusti. Kun pelaaja haluaa ostaa omistuksia, myös hänen varauksensa tulevat valittaviksi. Kun pelaaja haluaa varata, vain näkyvät omistukset tulevat näkyviin. Valitsinnappi lähettää pelaajan valitseman omistuksen nimen oikein Pelivelholle tilanteesta riippuen ostettavaksi / varattavaksi.
  * Jos pelaaaja yrittää ostaa omistuksen niin, että hänellä ei ole varaa siihen, ohjelma herjaa odotetusti ja valitsinkursori pysyy samassa paikassa.
  * Jos pelaaja yrittää varata omistusta, mutta hänellä on kolme varausta, ohjelma herjaa odotetusti eikä anna varata omistusta.
  * Jos pelaaja palaa osto-/varausnäkymästä takaisin vuoron toiminto -näkymään ja sen jälkeen menee taas ostamaan tai varaamaan, valitsin on siirtynyt takaisin "ensimmäiseen" näkyvään omistukseen. Tämä estää ongelman, että pelaaja jättäisi valitsimen omiin varauksiinsa (ostonäkymässä), menisi toimintonäkymän kautta varausnäkymään, jolloin valitsin olisi näkyvien omistusten "ulkopuolella" aiheuttaen kohtalokkaan virheen.

###Loppuikkunan toiminta

  * Ikkuna tulee esiin oikeaan aikaan
  * kaikki tekstit näkyvät odotetusti
  * uusintanappi aloittaa uuden pelin
  * lopettamisnappi aloittaa uuden pelin :grin:
