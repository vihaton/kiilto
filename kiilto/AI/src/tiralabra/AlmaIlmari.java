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
 */
public class AlmaIlmari {

    //todo AI ottaa huomioon pelissä olevat merkkihenkilöt
    //todo AI ottaa huomioon pöydällä näkyvillä olevat omistukset

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
     *          -merkkihenkilöt
     *          -arvovalta
     *      -mitä pöydässä on saatavilla?
     *          -nallekarkit
     *          -omistukset
     *          -merkkihenkilöt
     *      -mitä minun kannattaisi tehdä?
     *          -omistusten suhteelliset arvot?
     *          -potentiaalisten ostosten sopivuus nykyiseen omistusprofiiliin & nallekarkkeihin?
     *          -tulevan omistusprofiilin linjaus merkkihenkilön viekotteluun?
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
        Vuoro v;
        int karkkeja = keho.getKarkit().getKarkkienMaara();
        String poydastaOstettavanNimi = onkoVaraaOstaaOmistusPoydasta(keho, poyta);
        String kadestaOstettavanNimi = onkoVaraaOstaaOmistusVarauksista(keho);

        if (!kadestaOstettavanNimi.equals("ei")) {//lunastetaan varaus kädestä
            v = new Vuoro(VuoronToiminto.OSTA);
            v.ostettavanOmaisuudenNimi = kadestaOstettavanNimi;
        } else if (karkkeja < 8 && poyta.getMarkkinat().getKarkkienMaara() > 3) { //rohmutaan karkkeja, jos niitä saadaan kolme kerralla
            v = paataMitaNostetaan(keho, poyta);
        } else if (keho.getVaraukset().size() < 3 && karkkeja < 10 && poyta.getMarkkinat().getKasanKoko(0) != 0){ //varataan lisää omistuksia pöydästä jos kultakarkkeja on saatavilla ja ne mahtuvat käteen
            v = paataMitaVarataan(keho, poyta);
        } else if (!poydastaOstettavanNimi.equals("ei")) { //ostetaan omistuksia pöydästä, jos on varaa
            v = new Vuoro(VuoronToiminto.OSTA);
            v.ostettavanOmaisuudenNimi = poydastaOstettavanNimi;
        } else if (keho.getVaraukset().size() < 3) { //jos ei ollut varaa ostaa mitään, niin varataan sitten
            v = paataMitaVarataan(keho, poyta);
        } else if (karkkeja < 10 && poyta.getMarkkinat().getKarkkienMaara() > 0) { //jos on karkkeja mitä nostaa
            v = paataMitaNostetaan(keho, poyta);
        } else {
            v = new Vuoro(VuoronToiminto.ENTEEMITAAN);
        }

        return v;
    }

    /**
     *
     * @param keho mitä ohjataan
     * @param poyta millä pelataan
     * @return Vuoro-olio, johon on paketoitu mitä nallekarkkeja pitäisi nostaa
     */
    protected Vuoro paataMitaNostetaan(Pelaaja keho, Poyta poyta) {
        Vuoro v = new Vuoro(VuoronToiminto.NOSTA);
        v.mitaNallekarkkejaNostetaan = new int[]{0,0,0,0,0,0};
        Kasakokoelma markkinat = poyta.getMarkkinat();

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
     * @return Vuoro-olio, johon on paketoitu mikä omistus pitäisi varata
     */
    protected Vuoro paataMitaVarataan(Pelaaja keho, Poyta poyta) {
        Vuoro v = new Vuoro(VuoronToiminto.VARAA);
        v.varattavanOmistuksenNimi = onkoVaraaOstaaOmistusPoydasta(keho, poyta);

        //todo valitse varattava omistus fiksummin
        //jos yhteenkään omistukseen ei heti suoraan ole varaa, otetaan pöydästä ensimmäinen joka osuu käteen
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
