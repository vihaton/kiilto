### Toteutusdokumentti

#### Projektin moduulirakenne
![alt text][moduulit]

#### AI moduulin rakenne ja toiminta

Alla olevassa, selkeässä kuvaajassa on eritelty AI moduulin sisäiset riippuvuudet. Lisäksi huomautetaan riippuvuuksista logiikka-moduuliin nuolina `Logiikka` palluraan.

![alt text][ai]

Moduulin idea on seuraava: `AIMain` luokan ajamalla pystytään peluuttamaan tekoälyä itseään vastaan käyttämällä `Peluuttaja` luokkaa. Tämä sitten kirjoitutta `Kirjurilla` tulokset ylös. Näiden tulosten perusteella pystytään päättelemään tekoälyjen keskenäisestä suorituskyvystä asioita.

Toisaalta main -moduuli höydyntää AI -moduulin koodia käyttämällä `Peluuttajaa` tekoälyjen peluuttamiseen. Näin koko projektin kopypasta-koodin määrä on saatu minimoitua. Käyttäjän pelatessa itse tekoälyjä vastaan ajamalla [Kiilto.jar][jar] suoritetaan tekoälyjen vuorot kutsumalla `Peluuttajaa` joka käskee `AlmaIlmaria` joka hyödyntää logiikka -moduulia älyillessään.

#### Saavutetut aika- ja tilavaativuudet

Tekoälyn, eli `AlmaIlmarin`, vuoron suorittaminen on käytännössä *O(a)*, missä a on vakio. Jos aivan tarkkoja ollaan, niin tekoälyn käyttämän strategiasta ja pelitilanteesta riippuva valittava toiminto (tai sen selvittäminen) on *O(n)*, missä n on pelitilanteesta riippuva (hyvin pieni) kokonaisluku. Vuoron suorittamisen tilavaativuus on samaan tapaan vakio, *O(a)*.

Suorituskykytestauksessa tehtävä iterointi on hieman mielenkiintoisempi tapaus. Sen aika- ja tilavaativuus on *O(n)* AIMain-luokassa annettavista parametreista riippuen (pelien ja pelaajien määrä). Tehdään kuitenkin hieman tarkempi katsaus `Kirjurin` metodista `formatoiPelitTekstiksi(Lista<Peli> pelit)`, joka on sisäkkäisistä for-luupeista huolimatta aika(- ja tila)vaativuudeltaan *O(n)*. Metodi on sivumennen sanoen koko projektin pisin ja vaikeaselkoisin, joten se ansaitsee tulla näin erityisesti nostettua esiin.

Alla on yksi osa metodia, joka kokoaa peleihin talletetun informaation tekstiksi ja jäsentää sen ihmiselle luettavaan muotoon:

```     
    private Iterable<String> formatoiPelitTekstiksi(Lista<Peli> pelit) {
        ...
        int riveja = yhteenvetorivit + (pelaajia + 6) * peleja; //6 * pelaajien määrä rivejä per peli
        int yhteenvetorivit = 4 + pelaajia; //yhteenvetoon otsikko + pelaajien määrä + kierrosten määrän ka + voittoarvovallan ka + välirivi
        ...

        String[] rivit = new String[riveja];
        int osoitin = yhteenvetorivit;
        
        //selvitetään jokaisen pelin yhteenveto
        for (int i = 0; i < peleja; i++) {
            String[] yhteenveto = formatoiPeli(pelit.haeIndeksista(i));
            strategioidenVoitot[voittajanIndeksi(yhteenveto[0])]++;
            kierroksia += Integer.parseInt(yhteenveto[3]);
            voittoarvovalta += Integer.parseInt(yhteenveto[2]);
            rivit[osoitin++] = "\tPeli nro " + (i + 1);
            for (int j = 0; j < yhteenveto.length; j++) {
                rivit[osoitin++] = yhteenveto[j];
            }
        }
        
        ...
    }
```
Ulommasta luupista kutsuttavat metodit `formatoiPeli`, `haeIndeksista`, `voittajanIndeksi` sekä `Integer.parseInt` ovat kaikki vakioaikaisia ja -tilaisia, joten ulomman luupin aikavaativuus on *O(n)* missä n = pelattujen pelien määrä. Sisemmän luupin
 ```
            for (int j = 0; j < yhteenveto.length; j++) {
                rivit[osoitin++] = yhteenveto[j];
            }
 ```
suoritusaika riippuu yhteenvedon pituudesta. Yhteenveto on kuitenkin (käytännössä) vakiopituinen, ja vielä hyvin lyhyt sellainen (7-9 alkiota pelaajien määrästä riippuen). Tällöin sisemmän luupin suorittamiseen menevä aika on vakio, jolloin molempien luuppien aikavaativuus on yhdessä vain *O(n)*.

Vielä edelleen voidaan tarkentaa, että kyseinen aikavaativuus on 

*O(riveja) = O(yhteenvetorivit + (pelaajia + 6) * peleja) = O((4 + pelaajia) + (pelaajia + 6) * peleja)*

eli aikavaativuus riippuu pelaajien ja pelejen määrästä. Pelaajien määrä kuuluu välille [2,4], joten käytännössä ohjelman aika- ja tilavaativuus riippuu pelkästään peluutettavien pelien määrästä.

#### Puutteet ja parannusehdotukset

- Tekoälyjen optimoinnissa olisi vielä tekemistä, vaikka älyt jo nyt pystyvät peruspelurit voittamaan.
- Koneoppimispalikan tekeminen loppuun. Nyt jo on teknisesti mahdollista tallentaa pelatut pelit hyvin tarkasti Json:ksi käännettyinä olioina, jolloin ne pystyttäisiin halutessa helposti "herättämään henkiin", ja pelatuista tuloksista sitten päätellä mikä strategia on paras, josta edelleen parantaa nykyistä strategiaa.
- Javadoccien työstäminen jäi osalta projektia vähälle, mutta toisaalta olen pyrkinyt nimeämiskäytännöllä ja tarvittaessa kommenteilla tekemään koodista erittäin selkeää luettavaa.
- Käyttöliittymästä saa edelleen syövän, jos systeemistä haluaisi pelattavan niin se pitäisi toteuttaa täysin uudelleen, varmaankin Unity:llä tai jollain JS-frameworkillä.

[moduulit]: https://github.com/xvixvi/kiilto/raw/TLproduction/dokumentaatio/tiralabra/kuvat/kiilto_moduulit.png "projektin rakenne moduulitasolla"
[ai]: https://github.com/xvixvi/kiilto/raw/TLproduction/dokumentaatio/tiralabra/kuvat/AI-module.png "AI -moduulin rakenne"
[jar]: https://github.com/xvixvi/kiilto/tree/TLproduction/kiilto/out/artifacts/Kiilto_jar "Kiilto.jar"

