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
        //System.out.println("tekoälyn pitäisi pelata vuoro kehon puolesta:\n" + keho);
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
        String poydastaOstettavanNimi = onkoVaraaOstaaOmistusPoydasta(keho, poyta);
        String kadestaOstettavanNimi = onkoVaraaOstaaOmistusVarauksista(keho);

        if (!kadestaOstettavanNimi.equals("ei")) {
            v = new Vuoro(VuoronToiminto.OSTA);
            v.ostettavanOmaisuudenNimi = kadestaOstettavanNimi;
        } else if (karkkeja < 9 && poyta.getMarkkinat().getKarkkienMaara() > 3) { //ensisijaisesti rohmutaan karkkeja, jos niitä vielä on
            v = paataMitaNostetaan(keho, poyta);
        } else if (keho.getVaraukset().size() < 3){ //toissijaisesti varataan lisää omistuksia pöydästä
            v = paataMitaVarataan(keho, poyta);
        } else if (!poydastaOstettavanNimi.equals("ei")) { //kolmas vaihtoehto on ostaa omistuksia
            v = new Vuoro(VuoronToiminto.OSTA);
            v.ostettavanOmaisuudenNimi = poydastaOstettavanNimi;
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
