package tiralabra;

import logiikka.Pelivelho;
import logiikka.Poyta;
import logiikka.valineluokat.Kasakokoelma;
import org.junit.Before;
import org.junit.Test;
import tiralabra.vuorologiikka.Vuoro;
import tiralabra.vuorologiikka.VuoronToiminto;

import static org.junit.Assert.assertTrue;

/**
 * Created by vili on 11.8.2017.
 */
public class AlmaIlmariTest {

    private AlmaIlmari AI;
    private Pelivelho pv;
    private boolean[] humanAndAI = new boolean[]{false, true};
    private boolean[] twoAIs = new boolean[]{true, true};
    private boolean[] humanAndtwoAIs = new boolean[]{false, true, true};
    private boolean[] threeAIs = new boolean[]{true, true, true};

    @Before
    public void setAI() {
        pv = new Pelivelho();
        pv.luoPelaajatJaAIt(humanAndtwoAIs);
        pv.setPoyta(new Poyta(pv.getPelaajat()));
        AI = new AlmaIlmari(pv);
    }

    @Test
    public void paataMitaNostetaanTest() {
        //annetaan ensimmäinen tekoäly
        Vuoro v = AI.paataMitaNostetaan(pv.getPelaajat().get(1), pv.getPoyta());
        assertTrue("pelin alussa tekoäly aina nostaa karkkeja", v.toiminto.equals(VuoronToiminto.NOSTA));
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
}