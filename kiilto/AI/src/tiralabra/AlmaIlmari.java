package tiralabra;

import logiikka.Pelaaja;
import logiikka.Poyta;
import logiikka.omaisuusluokat.Omistus;
import logiikka.omaisuusluokat.Varaus;
import logiikka.valineluokat.Kasakokoelma;
import tiralabra.vuorologiikka.*;

/**
 * Created by vili on 4.8.2017.
 *
 * AI tekee päätökset sille annetun pelaajaolion tilanteen mukaan, eli ikään kuin tuo "älyn" ja "tavoitteen" tyhmälle oliolle.
 * On AI:n vastuulla tehdä laillisia siirtoja, tai vaihtoehtoisesti käsitellä pelivelhon huomautukset laittomista siirroista.
 *
 * Peliin oleellisesti liittyvät Pelaaja "keho" sekä Poyta "poyta" kuljetetaan luokan läpi metodin parametreina (ei luokkamuuttujina)
 * helpomman testattavuuden saavuttamiseksi.
 */
public class AlmaIlmari {

    //kehon tilanteen tallettamiseen algoritmien dynaamisuutta ajatellen
    private int karkkeja;
    private int varauksia;
    private String poydastaOstettavanNimi;
    private String kadestaOstettavanNimi;
    private int kuinkaMontaVoidaanNostaaKerralla;

    /**
     * AI suunnittelee pelaajalle vuoron pelaajan ja pöydän tilanteen mukaan.
     *
     * Pseudo idea:
     *
     * arvioi pelitilanne
     *      -mikä tilanne minulla on?
     *          -nallekarkit
     *          -varaukset
     *          -omistukset
     *          -merkkihenkilöt todo
     *          -arvovalta todo
     *      -mitä pöydässä on saatavilla?
     *          -nallekarkit
     *          -omistukset
     *          -merkkihenkilöt todo
     *      -mitä minun kannattaisi tehdä?
     *          -omistusten suhteelliset arvot? todo
     *          -potentiaalisten ostosten sopivuus nykyiseen omistusprofiiliin & nallekarkkeihin? todo
     *          -tulevan omistusprofiilin linjaus merkkihenkilön viekotteluun? todo
     *
     * tilanteen arviointi ja vuoron toteuttaminen on eriytetty,
     * jotta tulevaisuudessa edistyneempi tekoäly pystyisi harkitsemaan
     * eri vaihtoehtoja vapaammin.
     *
     * @param keho jota AlmaIlmari ohjailee
     * @param poyta jolla näkyy tämänhetkinen pelitilanne
     * @return suoritettavan vuoron käsikirjoitus
     */
    public Vuoro suunnitteleVuoro(Pelaaja keho, Poyta poyta) {
        return suunnitteleVuoro(keho, poyta, Strategia.OLETUS);
    }

     /**
     *
     * @param keho jota AlmaIlmari ohjailee
     * @param poyta jolla näkyy tämänhetkinen pelitilanne
     * @param strategia jonka mukaan määräytyy vuorontoimintojen priorisointijärjestys
     * @return suoritettavan vuoron käsikirjoitus
     */
    public Vuoro suunnitteleVuoro(Pelaaja keho, Poyta poyta, Strategia strategia) {
        paivitaApumuuttujat(keho, poyta);
        Vuoro v = new Vuoro(VuoronToiminto.ENTEEMITAAN);

        for (int i = 0; i < strategia.kuinkaMontaVaihtoehtoa(); i++) {
            v = kokeileValitaStrategianMukainenToiminto(keho, poyta, strategia.getToiminto(i));
            if (v != null)
                break;
        }

        return v;
    }

    private void paivitaApumuuttujat(Pelaaja keho, Poyta poyta) {
        this.karkkeja = keho.getKarkit().getKarkkienMaara();
        this.varauksia = keho.getVarauksienMaara();
        this.poydastaOstettavanNimi = onkoVaraaOstaaOmistusPoydasta(keho, poyta);
        this.kadestaOstettavanNimi = onkoVaraaOstaaOmistusVarauksista(keho);
        this.kuinkaMontaVoidaanNostaaKerralla = kuinkaMontaKarkkiaVoiNostaaKerralla(poyta);
    }

    /**
     *
     * @param keho jota ohjataan
     * @param poyta jolla pelataan
     * @param toiminto joka haluttaisiin seuraavaksi toteuttaa
     * @return Vuoro, joka on joko null (haluttua toiminnallisuutta ei pystytty toteuttamaan) tai sisältää suunnitelman vuoron toteutuksesta.
     */
    protected Vuoro kokeileValitaStrategianMukainenToiminto(Pelaaja keho, Poyta poyta, VuoronToiminto toiminto) {
        Vuoro v = null;
        switch (toiminto) {
            case NOSTA_KOLME:
                if (kuinkaMontaVoidaanNostaaKerralla == 3 && karkkeja < 8) {
                    v = paataMitaNostetaan(keho, poyta, kuinkaMontaVoidaanNostaaKerralla);
                    v.toiminto = VuoronToiminto.NOSTA_KOLME;
                }
                break;

            case NOSTA_KAKSI:
                if (kuinkaMontaVoidaanNostaaKerralla == 2 && karkkeja < 10) {
                    v = paataMitaNostetaan(keho, poyta, kuinkaMontaVoidaanNostaaKerralla);
                    v.toiminto = VuoronToiminto.NOSTA_KAKSI;
                }
                break;

            case NOSTA:
                if (kuinkaMontaVoidaanNostaaKerralla > 0 && karkkeja < 10) {
                    v = paataMitaNostetaan(keho, poyta, kuinkaMontaVoidaanNostaaKerralla);
                }
                break;

            case VARAA_ARVOKAS:
                if (varauksia < 3 && poyta.getMarkkinat().getKasanKoko(0) != 0 && karkkeja < 10)
                    v = paataMitaVarataan(keho, poyta, VuoronToiminto.VARAA_ARVOKAS);
                break;

            case VARAA_HALPA:
                if (varauksia < 3)
                    v = paataMitaVarataan(keho, poyta, VuoronToiminto.VARAA_HALPA);
                break;

            case OSTA_VARAUS:
                if (!kadestaOstettavanNimi.equals("ei")) {
                    v = new Vuoro(VuoronToiminto.OSTA_VARAUS);
                    v.ostettavanOmaisuudenNimi = kadestaOstettavanNimi;
                }
                break;

            case OSTA_POYDASTA:
                if (!poydastaOstettavanNimi.equals("ei")) {
                    v = new Vuoro(VuoronToiminto.OSTA_POYDASTA);
                    v.ostettavanOmaisuudenNimi = poydastaOstettavanNimi;
                }
                break;

            default:
                v = new Vuoro(VuoronToiminto.ENTEEMITAAN);
                break;
        }
        return v;
    }


        /**
         *
         * @param poyta jolla pelataan
         * @return int kuinka monta karkkia voidaan maksimissaan nostaa vuorolla
         */
    protected int kuinkaMontaKarkkiaVoiNostaaKerralla(Poyta poyta) {
        Kasakokoelma markkinat = poyta.getMarkkinat();
        int kultaisia = markkinat.getKasanKoko(0);
        int yhteensa = markkinat.getKarkkienMaara();
        int kuinkaMonessaKasassa = markkinat.kuinkaMonessaTavallisessaKasassaOnKarkkeja();

        if (kultaisia == yhteensa) {
            return 0;
        } else if (yhteensa - kultaisia < 4 && kuinkaMonessaKasassa == 1) {
            return 1;
        } else if ((kuinkaMonessaKasassa == 1 && yhteensa - kultaisia > 3) || kuinkaMonessaKasassa == 2) {
            return 2;
        } else
            return 3;
    }

    /**
     *
     * @param keho mitä ohjataan
     * @param poyta millä pelataan
     * @return Vuoro-olio, johon on paketoitu mitä nallekarkkeja pitäisi nostaa
     */
    protected Vuoro paataMitaNostetaan(Pelaaja keho, Poyta poyta) {
        return paataMitaNostetaan(keho, poyta, -1);
    }

    /**
     *
     * @param keho mitä ohjataan
     * @param poyta millä pelataan
     * @param kuinkaMontaVoidaanNostaaKerralla
     * @return Vuoro-olio, johon on paketoitu mitä nallekarkkeja pitäisi nostaa
     */
    protected Vuoro paataMitaNostetaan(Pelaaja keho, Poyta poyta, int kuinkaMontaVoidaanNostaaKerralla) {
        Vuoro v = new Vuoro(VuoronToiminto.NOSTA);
        v.mitaNallekarkkejaNostetaan = new int[]{0,0,0,0,0,0};
        Kasakokoelma markkinat = poyta.getMarkkinat();
        if (kuinkaMontaVoidaanNostaaKerralla == -1) { //undefined
            kuinkaMontaVoidaanNostaaKerralla = kuinkaMontaKarkkiaVoiNostaaKerralla(poyta);
        }
        //todo käytä tietoa maksiminostomäärästä hyödyksi

        //jos karkkeja on 7 tai vähemmän, voidaan nostaa 3 eriväristä karkkia, muuten nostettavana on 10-määrä
        int tilaaNostaa = keho.getKarkit().getKarkkienMaara() < 8 ? 3 : 10 - keho.getKarkit().getKarkkienMaara();
        int nostetaan = 0;
        for (int i = 1; i < 6; i++) { //kasoille mistä voi nostaa
            int karkkejaSaatavilla = markkinat.getKasanKoko(i);
            if (karkkejaSaatavilla > 0) {
                v.mitaNallekarkkejaNostetaan[i]++;
                tilaaNostaa--;
                nostetaan++;
            }
            if (tilaaNostaa == 0) {
                return v;
            }
        }
        //karkkikasoista ei saanut kolmea eriväristä karkkia.

        if (nostetaan == 2) //valittuna on jo kaksi, otetaan ne
            return v;
        else if (nostetaan == 1) { //valittuna on yksi, eli muut kasat ovat olleet tyhjiä ja pelaajalla on vielä tilaa nostaa
            for (int i = 1; i < 6; i++) {
                if (markkinat.getKasanKoko(i) > 3) { //kasassa on oltava aluksi väh. 4 karkkia, että siitä voi nostaa 2 samanväristä
                    v.mitaNallekarkkejaNostetaan[i]++;
                    return v;
                }
            }
        }

        return v;
    }

    /**
     *
     * @param keho jota ohjataan
     * @param poyta jolla pelataan
     * @param toiminto varataanko arvokas vai halpa omistus?
     * @return Vuoro-olio, johon on paketoitu mikä omistus pitäisi varata
     */
    protected Vuoro paataMitaVarataan(Pelaaja keho, Poyta poyta, VuoronToiminto toiminto) {
        Vuoro v = new Vuoro(toiminto);
        //todo
        /*
        if (toiminto.equals(VuoronToiminto.VARAA_ARVOKAS)) {
        } else
        */
            v.varattavanOmistuksenNimi = onkoVaraaOstaaOmistusPoydasta(keho, poyta);

        //todo valitse varattava omistus fiksummin
        //jos edellä olevat metodit eivät tuottaneet tulosta, niin varataan nyt edes jotain
        if (v.varattavanOmistuksenNimi.equals("ei")) {
            v.varattavanOmistuksenNimi = poyta.getNakyvienNimet().get(0);
        }
        return v;
    }

    /**
     *
     * @param keho jota ohjataan
     * @param poyta jolla pelataan
     * @return String "ei", jos ei ole varaa ostaa omistusta, muuten ostettavan omaisuuden nimi.
     */
    protected String onkoVaraaOstaaOmistusPoydasta(Pelaaja keho, Poyta poyta) {
        String nimi = "ei";
        for (Omistus o : poyta.getNakyvatOmistukset()) {
            if (keho.onkoVaraa(o)) {
                nimi = o.getNimi();
            }
        }
        return nimi;
    }

    /**
     *
     * @param keho jota ohjataan
     * @return String "ei", jos ei ole varaa, muuten kyseisen omistuksen nimi.
     */
    protected String onkoVaraaOstaaOmistusVarauksista(Pelaaja keho) {
        String nimi = "ei";
        for (Varaus v : keho.getVaraukset()) {
            if (keho.onkoVaraa(v.getOmistus())) {
                nimi = v.getOmistus().getNimi();
            }
        }
        return nimi;
    }
}
