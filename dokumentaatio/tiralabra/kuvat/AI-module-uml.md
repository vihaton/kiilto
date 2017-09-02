[AlmaIlmari], [Peluuttaja], [Kirjuri], [Strategia], [Vuoro] [VuoronToiminto], [AIMain], [Logiikka], [Peli], [Kierros], [AInVuoro], [Lista]

[AIMain]->[Peluuttaja]
[AIMain]-<[Strategia]
[AIMain]->[Logiikka]

[Peluuttaja]->[AlmaIlmari]
[Peluuttaja]->[Strategia]
[Peluuttaja]->[Kirjuri]
[Peluuttaja]->[Vuoro]
[Peluuttaja]->[Peli]
[Peluuttaja]->[Kierros]
[Peluuttaja]->[AInVuoro]
[Peluuttaja]->[Logiikka]

[Kirjuri]->[Peli]
[Kirjuri]->[Strategia]
[Kirjuri]->[Lista]
[Kirjuri]->[JsonParseri]

[JsonParseri]->[Peli]
[JsonParseri]->[Strategia]
[JsonParseri]->[Lista]

[AlmaIlmari]->[Vuoro]
[AlmaIlmari]->[Strategia]
[AlmaIlmari]->[VuoronToiminto]
[AlmaIlmari]->[Logiikka]

[Peli]->[Kierros]
[Peli]->[Strategia]
[Peli]->[Lista]

[Kierros]->[Lista]
[Kierros]->[AInVuoro]

[AInVuoro]->[Vuoro]
[AInVuoro]->[Strategia]

[Strategia]->[VuoronToiminto]
[Vuoro]->[VuoronToiminto]
