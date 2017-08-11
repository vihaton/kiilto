package tiralabra;

import logiikka.*;
import logiikka.valineluokat.Kasakokoelma;
import tiralabra.vuorologiikka.*;

/**
 * Created by vili on 4.8.2017.
 *
 * AI tekee päätökset sille annetun pelaajaolion tilanteen mukaan, eli ikään kuin tuo "älyn" ja "tavoitteen" tyhmälle oliolle.
 * On AI:n vastuulla tehdä laillisia siirtoja, tai vaihtoehtoisesti käsitellä pelivelhon huomautukset laittomista siirroista.
 */
public class AlmaIlmari {

    private Pelivelho pelivelho;

    public AlmaIlmari(Pelivelho pelivelho) {
        this.pelivelho = pelivelho;
    }

    //todo AI ottaa huomioon pelissä olevat merkkihenkilöt
    //todo AI ottaa huomioon pöydällä näkyvillä olevat omistukset

    /**
     * AI pelaa vuoron pelaajan puolesta, kuten oikeakin pelaaja (eli kutsuu pelivelhon metodeita toiminnan mukaan).
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
     * @param keho jota AlmaIlmari ohjailee
     * @param poyta jolla näkyy tämänhetkinen pelitilanne
     */
    public void pelaaVuoro(Pelaaja keho, Poyta poyta) {
        System.out.println("tekoälyn pitäisi pelata vuoro kehon puolesta:\n" + keho);

        //arvioidaan
        Vuoro mitaTehdaan = arvioiPelitilanne(keho, poyta);

        //toimitaan
        toteutaVuoro(mitaTehdaan);

        //todo AI tekee aina laillisen siirron
    }

    protected Vuoro arvioiPelitilanne(Pelaaja keho, Poyta poyta) {
        Vuoro v;
        int karkkeja = keho.getKarkit().getKarkkienMaara();
        if (karkkeja < 9) {
            v = paataMitaNostetaan(keho, poyta);
        } else if (keho.getVaraukset().size() < 3){
            v = new Vuoro(VuoronToiminto.VARAA);
        } else {
            v = new Vuoro(VuoronToiminto.OSTA);
        }

        v.varattavanOmaisuudenNimi = poyta.getNakyvienNimet().get(0);
        v.ostettavanOmaisuudenNimi = poyta.getNakyvienNimet().get(0);
        return v;
    }

    protected void toteutaVuoro(Vuoro v) {
        switch (v.toiminto) {
            case NOSTA: pelivelho.nostaNallekarkkeja(v.mitaNallekarkkejaNostetaan);
                        break;
            case VARAA: pelivelho.varaa(v.varattavanOmaisuudenNimi);
                        break;
            case OSTA:  pelivelho.osta(v.ostettavanOmaisuudenNimi);
        }
    }

    protected Vuoro paataMitaNostetaan(Pelaaja keho, Poyta poyta) {
        Vuoro v = new Vuoro(VuoronToiminto.NOSTA);
        v.mitaNallekarkkejaNostetaan = new int[]{0,0,0,0,0};
        int nostettavia = 0;
        Kasakokoelma markkinat = poyta.getMarkkinat();
        while (nostettavia <= 3 && keho.getKarkit().getKarkkienMaara() + nostettavia <= 10) {
            for (int i = 1; i < 6; i++) {
                int karkkejaSaatavilla = markkinat.getKasanKoko(i);
                if (karkkejaSaatavilla > 0) {
                    v.mitaNallekarkkejaNostetaan[i-1]++;
                    nostettavia++;
                }
                if (nostettavia > 2) {
                    break;
                }
            }
        }
        return v;
    }
}
