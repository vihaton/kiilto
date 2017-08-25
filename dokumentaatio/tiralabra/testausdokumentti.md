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

Käytännössä tämä toteutetaan peluuttamalla älyä itseään vastaan (katso [Peluuttaja][peluuttaja]), jonka jälkeen tarvittavia tietorakenteita (Peli, Kierros jne.) hyväksikäyttäen tulokset tallennetaan tiedostoon [results](TODO LINK HERE) käyttämällä hyödyksi IO kansiossa olevaa [Kirjuria][kirjuri] ja Json parseria. Kun pelejä on pelattu tarpeeksi suuri määrä, voidaan tuloksista ja käytetyistä strategioista vetää johtopäätöksiä eri strategioiden 'suorituskyvystä', ja vastata edellä esitettyihin kysymyksiin.



[AI]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/AlmaIlmari.java "AlmariIlmari.java"
[strat]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/tietorakenteet/Strategia.java "Strategia.java"
[kirjuri]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/IO/Kirjuri.java "Kirjuri.java"
[peluuttaja]: https://github.com/xvixvi/kiilto/blob/TLproduction/kiilto/AI/src/tiralabra/iterointi/Peluuttaja.java "Peluuttaja.java"
