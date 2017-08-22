## viikkoraportti 5

Maanantaina oli vuorossa toiminnallisuuden viimeistely. Kehitin tekoälylle kattavaa toimintarepertuaaria, jotta se osaisi kaikki mahdolliset toiminnot. Kattava testaus luoduille ominaisuuksille.

Tiistaina jatkoin tästä, ja suunnittelin+toteutin+testasin Strategia -palikan. Nyt tekoäly osaa peluuttaa annettua kehoa annetussa pelitilanteessa annetulla strategialla. Tämä mahdollistaa eri strategioiden vertailun, metodien joustavan testauksen sekä tekoälyn joustavan käyttämisen. 

Älyä ei siis ole sidottu mihinkään kehoon tai strategiaan. Tämä mahdollistaa suuren joustavuuden, mutta asettaa rajoituksia älyn suorituskyvylle: nyt äly ei pysty tekemään (tehokkaasti) yhtä vuoroa pidempiä suunnitelmia, vaan se pidempiaikaista suunnittelua varten sen pitää joka vuoro "laskea" nollasta, sillä dynaamisuuteen ei ole mahdollisuutta. Tämä ei kuitenkaan tule olemaan ongelma, sillä peli on luonteeltaan vaikea ennakoida pitkälle: pelin kulku riippuu paljolti omistus-korttien (satunnaisesta) järjestyksestä.

Toisaalta pidemmän aikavälin suunnittelu on mahdollista lyhyen aikavälin valintoja priorisoidessa. Jos älyn kehittämiseen olisi enemmän aikaa, voisi systeemistä tehdä hionovaraisemman useammilla parametreillä, peluuttaa eri strategioita eri painotuksilla toisiaan vastaan x-miljoonaa iteraatiota ja lopuksi tarkistaa kuka pärjäsi parhaiten. Tähän kun lisäisi vielä parametrien säätämisen testi-iteraatioiden perusteella, olisi kyse koneoppimisesta. Mielenkiintoinen ajatus, mutta turha toteuttaa tämmöiseen käyttöliittymään, kun kukaan ei peliä ikinä tule pelaamaan!

21.8. *4.5h*
- äly osaa tehdä kaikki mahdolliset temput
- kattava testaus

22.8 *4.5h*
- Strategia -palikan implementoiminen
- Kattava integraatiotestaus, testaustapausten suunnittelu
- Tekoälyn jatkokehityksen ja potentiaalisen koneoppimisen maalailu
