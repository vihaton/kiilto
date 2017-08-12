package tiralabra;

import logiikka.Pelaaja;
import logiikka.Poyta;
import logiikka.omaisuusluokat.Omistus;
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
     * AI pelaa vuoron pelaajan puolesta, kuten oikeakin pelaaja (eli kutsuu pelivelhon metodeita toiminnan mukaan).
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
     * käytä vuoro
     *      -nosta karkkeja
     *      -varaa omaisuutta
     *      -osta omaisuutta
     *          -pöydästä
     *          -kädestä
     *
     * tilanteen arviointi ja vuoron toteuttaminen on eriytetty,
     * jotta tulevaisuudessa edistyneempi tekoäly pystyisi harkitsemaan
     * eri vaihtoehtoja vapaammin.
     *
     * @param keho jota AlmaIlmari ohjailee
     * @param poyta jolla näkyy tämänhetkinen pelitilanne
     */
    public Vuoro suunnitteleVuoro(Pelaaja keho, Poyta poyta) {
        System.out.println("tekoälyn pitäisi pelata vuoro kehon puolesta:\n" + keho);

        //arvioidaan
        Vuoro mitaTehdaan = arvioiPelitilanne(keho, poyta);

        //todo AI tekee aina laillisen siirron

        return mitaTehdaan;
    }

    /**
     *
     * @param keho jota ohjataan
     * @param poyta jolla pelataan
     * @return suunnitelman toteutettavasta vuorosta
     */
    protected Vuoro arvioiPelitilanne(Pelaaja keho, Poyta poyta) {
        Vuoro v;
        int karkkeja = keho.getKarkit().getKarkkienMaara();
        if (karkkeja < 9) {
            v = paataMitaNostetaan(keho, poyta);
        } else if (keho.getVaraukset().size() < 3){
            v = paataMitaVarataan(keho, poyta);
        } else {
            v = new Vuoro(VuoronToiminto.OSTA);
        }

        v.varattavanOmistuksenNimi = poyta.getNakyvienNimet().get(0);
        v.ostettavanOmaisuudenNimi = poyta.getNakyvienNimet().get(0);
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
        int nostettavia = 0;
        Kasakokoelma markkinat = poyta.getMarkkinat();
        while (nostettavia < 3 && keho.getKarkit().getKarkkienMaara() + nostettavia < 10) {
            for (int i = 1; i < 6; i++) {
                int karkkejaSaatavilla = markkinat.getKasanKoko(i);
                if (karkkejaSaatavilla > 0) {
                    v.mitaNallekarkkejaNostetaan[i]++;
                    nostettavia++;
                }
                if (nostettavia > 2) {
                    break;
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
        v.varattavanOmistuksenNimi = koitaValitaOmistusJohonOnHetiVaraa(keho, poyta);

        //todo valitse varattava omistus fiksummin
        //jos yhteenkään omistukseen ei heti suoraan ole varaa, otetaan pöydästä ensimmäinen joka osuu käteen
        if (v.varattavanOmistuksenNimi == null) {
            v.varattavanOmistuksenNimi = poyta.getNakyvienNimet().get(0);
        }
        return v;
    }

    /**
     *
     * @param keho jota ohjaillaan
     * @param poyta jolla pelataan
     * @return pöydällä olevan omistuksen nimi, minkä voisi heti olemassa olevilla varoilla lunastaa, null jos sellaista ei ole.
     */
    protected String koitaValitaOmistusJohonOnHetiVaraa(Pelaaja keho, Poyta poyta) {
        //todo tämä on fiksuinta testata mockaamalla, mockito maven depencyihin
        for (Omistus o : poyta.getNakyvatOmistukset()) {
            if (keho.onkoVaraa(o)) {
                return o.getNimi();
            }
        }
        return null;
    }
}
