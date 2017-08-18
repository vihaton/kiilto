package tiralabra;

import logiikka.Pelaaja;
import logiikka.Pelinpystyttaja;
import logiikka.Poyta;
import logiikka.valineluokat.Kasakokoelma;
import org.junit.Before;
import org.junit.Test;
import tiralabra.vuorologiikka.Vuoro;
import tiralabra.vuorologiikka.VuoronToiminto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vili on 11.8.2017.
 */
public class AlmaIlmariTest {

    private AlmaIlmari AI;
    private Pelinpystyttaja pp;
    private Pelaaja keho;
    private Poyta poyta;
    private boolean[] humanAndAI = new boolean[]{false, true};
    private boolean[] twoAIs = new boolean[]{true, true};
    private boolean[] humanAndtwoAIs = new boolean[]{false, true, true};
    private boolean[] threeAIs = new boolean[]{true, true, true};

    @Before
    public void setAI() {
        pp = new Pelinpystyttaja(humanAndtwoAIs);
        AI = new AlmaIlmari();
        keho = pp.pelaajat.get(1);
        poyta = pp.poyta;
    }

    @Test
    public void suunnitteleVuoroReturnsValidVuoro() {
        Vuoro v = AI.suunnitteleVuoro(keho, poyta);
        assertTrue(v.toiminto != null);
    }

    @Test
    public void onkoVaraaOstaaOmistusPoydasta() {
        String nimi = AI.onkoVaraaOstaaOmistusPoydasta(keho, poyta);
        assertTrue("ei oo varaa jos ei oo rahaa", nimi.equals("ei"));
        keho.setKarkit(new int[]{5,1,1,1,1,1});
        nimi = AI.onkoVaraaOstaaOmistusPoydasta(keho, poyta);
        assertTrue("jos on parhaat rahat niin on aina varaa", nimi != null);
        assertTrue("varattava omaisuus löytyy pöydästä", poyta.getNakyvienNimet().contains(nimi));
    }

    @Test
    public void paataMitaNostetaanTest() {
        //annetaan ensimmäinen tekoäly
        Vuoro v = AI.paataMitaNostetaan(keho, poyta);
        assertTrue("vuoron toiminto on oikein", v.toiminto.equals(VuoronToiminto.NOSTA));
        Kasakokoelma nostetutKarkit = new Kasakokoelma(v.mitaNallekarkkejaNostetaan);
        assertTrue("on kannattavampaa nostaa kolme karkkia kuin vain kaksi, kun ei ole varauksia ja karkkeja on saatavilla.\n" +
                        "karkkeja nostettiin " + nostetutKarkit.getKarkkienMaara(),
                new Kasakokoelma(v.mitaNallekarkkejaNostetaan).getKarkkienMaara() == 3);
        assertTrue("koskaan äly ei voi nostaa kultaista karkkia\n" + nostetutKarkit, nostetutKarkit.getKasa(0).getKoko() == 0);
        for (int i = 1; i < 6; i++) {
            assertTrue("jos karkkeja nostetaan 3kpl, niin kaikkien täytyy olla erivärisiä",
                    nostetutKarkit.getKasa(i).getKoko() < 2);
        }
    }

    @Test
    public void paataMitaVarataanTest() {
        Vuoro v = AI.paataMitaVarataan(keho, poyta);
        assertTrue(v.toiminto.equals(VuoronToiminto.VARAA));
        assertTrue("varattavan omistuksen pitäisi löytyä pöydältä!",
                poyta.getNakyvienNimet().contains(v.varattavanOmistuksenNimi));
    }
}
