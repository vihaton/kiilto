## Testausdokumentti

### Mitä testataan

Tiralabrassa keskityn pelkästään AI -moduuliin, myös testaamispuolella. Käytännössä tämä tarkoittaa yksikkö- integraatio- ja (tietynlaista) suorituskykytestausta AI:moduulille. Toki logiikkamoduuliakin testataan kattavasti, mutta tämä (jo kerran arvosteltu) kokonaisuus ei kuulu tiralabran alueeseen.

Kattava, merkityksellisten asioiden **yksikkötestaus** on softan tekemisen lähtökohta. Aikaisemmat kokemukset ovat opettaneet tunnistamaan ja keskittymään virhealttiisen koodiin, ja välttämään turhien getterien ja settereiden kirjoittamista ja testaamista alusta alkaen. Myös kaikki IO jätetään käsin testattavaksi.

Täydellistä rivi- ja mutaationtappamiskattavuutta tärkeämpänä pidän testitapausten huolellisena suunnitteluna ja implementointina: on paljon hankalampaa (ja työläämpää) kirjoittaa kattavat, monipuoliset testitapaukset kuin saada 100% rivikattavuus ja lähes täydellinen mutaationtappokattavuus.

**Integraatiotestaus** on jätetty AI-moduulissa kevyemmälle, jääden pelkästään siihen että metodit kutsuvat ja palauttavat oikeita asioita, softa kääntyy ja että pelatessa tekoäly käyttäytyy odotetulla (tehokkaalla + fiksulla/tyhmällä + laillisella) tavalla.

Kaikista mielenkiintoisinta tässä tapauksessa on tekoälyn **suorituskykytestaaminen**. Tämä ei liity niinkään syötteen kokoon tai malliin: käytännössä pelissä on tekoälyn näkökulmasta kaksi syötettä, jotka molemmat ovat vakiokokoisia ja suhteellisen vakiomallisia. Testaaminen painottuukin tekoälyjen *suoriutumisen* testaamiseen.

### Suorituskykytestaus

Suorituskyky, tai ehkä paremminkin suoriutumiskyky, -testauksessa tekoälyä [AlmaIlmari][AI] peluutetaan itseään vastaan erilaisilla [Strategioilla][strat] ja testataan:

- miten äly pärjää itseään vastaan eri strategioilla pelatessa;
- mikä strategioista on paras;
- onko tekoälyjen välillä paras strategia hyvä myös ihmistä vastaan pelatessa?

Käytännössä tämä toteutetaan peluuttamalla älyä itseään vastaan (katso [Peluuttaja][peluuttaja]), jonka jälkeen tarvittavia tietorakenteita (Peli, Kierros jne.) hyväksikäyttäen tulokset tallennetaan tiedostoon [peliraportti][peliraportti] käyttämällä hyödyksi IO kansiossa olevaa [Kirjuria][kirjuri] ja Json parseria. Kun pelejä on pelattu tarpeeksi suuri määrä, voidaan tuloksista ja käytetyistä strategioista vetää johtopäätöksiä eri strategioiden 'suorituskyvystä', ja vastata edellä esitettyihin kysymyksiin.

#### Tuloksia

Suorittamalla main metodin [AIMain.java][aimain] luokasta, ja muuttamalla metodissa Peluuttajalle annettavia parametreja, voidaan peluuttaa tekoälyjä vastakkain ja kerätä tuloksia kiilto/AI/logs/ kansioon.

Peluutettaessa neljää oletusstrategiaa vastakkain 10000 peliä, saatiin seuraavat tulokset:

```
Pelejä 10000kpl, tekoälyjä 4kpl
Strategia OLETUS 		voitot 424kpl	4.24%
Strategia HAMSTRAA_KARKKEJA 	voitot 71kpl	0.71%
Strategia HAMSTRAA_OMISTUKSIA 	voitot 5420kpl	54.2%
Strategia TEHOKAS 		voitot 4085kpl	40.85%
kierroksia pelissä keskimäärin 	28.3397
voittoarvovalta keskimäärin 	16.6009
```

Tuloksista näkyy selvästi, että omistusten hamstraaminen ja tehokkuus johtavat muita strategioita parempiin tuloksiin. Huomattavaa on myös, että kun TEHOKAS -strategia jätetään pois, omistusten hamstraamisesta tulee ylivoimainen, mutta pelin pituus kierroksissa myös kasvaa:

```
Pelejä 1000kpl, tekoälyjä 3kpl
Strategia OLETUS 		voitot 130kpl	13.0%
Strategia HAMSTRAA_KARKKEJA 	voitot 25kpl	2.5%
Strategia HAMSTRAA_OMISTUKSIA 	voitot 845kpl	84.5%
kierroksia pelissä keskimäärin 	30.632
voittoarvovalta keskimäärin 	16.577
```

Jos heikoiten pärjäävä strategia karkkien hamstraamisesta korvataan hyväksi havaittuun TEHOKAS -strategiaan, saadaan seuraava yhteenveto:

```
Pelejä 10000kpl, tekoälyjä 3kpl
Strategia OLETUS 		voitot 522kpl	5.22%
Strategia TEHOKAS 		voitot 5328kpl	53.28%
Strategia HAMSTRAA_OMISTUKSIA 	voitot 4150kpl	41.5%
kierroksia pelissä keskimäärin 	28.8396
voittoarvovalta keskimäärin 	16.3542
```

Toisin sanoen pelin keskimääräinen kierrosmäärä laskee huomattavasti, kun kaksi kovinta strategiaa pelaa ovat läsnä, eikä pelin lyheneminen johdu esimerkiksi pelaajien määrästä. Huomattavaa on myös tehokkaan ja omistuksia hamstraavan strategian voittoprosenttien samankaltaisuus 3 ja 4 pelaajan peleissä. Voimasuhteisiin ei siis vaikuta pelaajien (ja samalla jaossa olevien karkkien ja merkkihenkilöiden) määrä.



[aimain]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/iterointi/AIMain.java "AIMain.java"
[peliraportti]: https://github.com/xvixvi/kiilto/blob/TLproduction/dokumentaatio/tiralabra/pelit_10000kpl_02092017215513.txt "peliraportti"
[AI]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/AlmaIlmari.java "AlmariIlmari.java"
[strat]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/tietorakenteet/Strategia.java "Strategia.java"
[kirjuri]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/IO/Kirjuri.java "Kirjuri.java"
[peluuttaja]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/iterointi/Peluuttaja.java "Peluuttaja.java"
