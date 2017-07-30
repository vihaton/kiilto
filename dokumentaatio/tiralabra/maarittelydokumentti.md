## Määrittelydokumentti

### algoritmit ja tietorakenteet

- algoritmi tekoälylle pelitilanteiden arviointiin
- algoritmi tekoälylle käsikorttien arvottamiseen
- algoritmi tekoälylle vuoron suorittamiseen
- osa tai kaikki seuraavista tarpeen mukaan: lista, keko, puu

### ongelma

Tarkoitus on tuottaa tekoäly, joka pystyy pelaamaan järkevästi javalabrassa tekemääni peliä, kiiltoa. Yllä mainitut algoritmit ovat välttämättömiä tämän saavuttamisessa, ja tietorakenteet valitsen algoritmien lopullisen muodon mukaan.

### saadut syötteet

Ohjelma saa syötteekseen pelitilannedataa itse peliltä, ja valitsee toimintansa pelitilanteen mukaan.

### aika- ja tilavaativuudet

Tekoälyn ei ole tarkoitus olla kovinkaan raskas: pelissä on mukana satunnaisuutta ja tuntemattomia muuttujia, joten osa päätöksistä tulee perustumaan todennäköisyyksille. Tekoälyn vuoron tulee olla ohi viimeistään tietyn ajan jälkeen, ja tässä mielessä aikavaativuus on vakio. Algoritmisesti aikavaativuus voi olla raskas, mutta sitä rajoitetaan keinoteikoisesti tarvittaessa (jos esimerkiksi joudutaan käymään pelipuita, vaativuus voi olla pahimmillaan rajoittamattomalla läpikäynnillä luokkaa O a^n). Tilavaativuus olisi mukava saada vakioiksi, mutta pelipuiden tapauksessa sekin voi levähtää käsiin.
