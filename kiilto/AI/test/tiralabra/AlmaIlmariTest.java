package tiralabra;

import logiikka.Pelaaja;
import logiikka.Pelinpystyttaja;
import logiikka.Poyta;
import logiikka.valineluokat.Kasakokoelma;
import org.junit.Before;
import org.junit.Test;
import tiralabra.vuorologiikka.Vuoro;
import tiralabra.vuorologiikka.VuoronToiminto;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

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
    public void onkoVaraaOstaaOmistusVarauksista() {
        String nimi = AI.onkoVaraaOstaaOmistusVarauksista(keho);
        assertTrue("jos ei ole varauksia, ei voi lunastaa", nimi.equals("ei"));
        poyta.teeVaraus(keho, Integer.parseInt(poyta.getNakyvienNimet().get(0)));
        nimi = AI.onkoVaraaOstaaOmistusVarauksista(keho);
        assertTrue("ei oo varaa,ni ei oo varaa", nimi.equals("ei"));
        keho.setKarkit(new int[]{5,1,1,1,1,1});
        nimi = AI.onkoVaraaOstaaOmistusVarauksista(keho);
        assertTrue("maksimikarkeilla pitää olla varaa ostaa mikä tahansa 1 pakan omistus", !nimi.equals("ei"));
    }

    @Test
    public void nostaOletuksenaKolmeKarkkiaTest() {
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
    public void nostaKaksiKarkkiaJosTilaEiRiita() {
        int[][] varallisuustilanteet = new int[][]{
            new int[]{0,2,2,1,1,2}, //kaksi eriväristä
            new int[]{0,2,2,2,0,2}, //kaksi samanväristä
            new int[]{1,0,2,2,1,2}  //kultainen lasketaan myös
        };
        for (int i = 0; i < varallisuustilanteet.length; i++) {
            keho.setKarkit(varallisuustilanteet[i]);
            Vuoro v = AI.paataMitaNostetaan(keho, poyta);
            Kasakokoelma nostetutKarkit = new Kasakokoelma(v.mitaNallekarkkejaNostetaan);
            assertTrue("karkkeja on nostettu 2kpl", nostetutKarkit.getKarkkienMaara() == 2);
        }
    }

    @Test
    public void nostaKaksiKarkkiaJosPoydassaEiOleKolmeaNostettavaa() {
        int[][] markkinatilanteet = new int[][]{
                new int[]{5,0,0,2,2,0},
                new int[]{5,0,0,5,2,0},
                new int[]{5,0,0,5,0,0}
        };
        for (int i = 0; i < markkinatilanteet.length; i++) {
            Kasakokoelma markkinatilanne = new Kasakokoelma(markkinatilanteet[i]);
            poyta.setKarkkimarkkinat(markkinatilanne);
            Vuoro v = AI.paataMitaNostetaan(keho, poyta);
            Kasakokoelma nostetutKarkit = new Kasakokoelma(v.mitaNallekarkkejaNostetaan);
            assertTrue("karkkeja on nostettu 2kpl", nostetutKarkit.getKarkkienMaara() == 2);
        }
        poyta.setKarkkimarkkinat(new Kasakokoelma(new int[]{5,0,0,3,0,0}));
        Vuoro v = AI.paataMitaNostetaan(keho, poyta);
        assertTrue("karkkeja voi nostaa vain yhden, muuten laiton siirto", new Kasakokoelma(v.mitaNallekarkkejaNostetaan).getKarkkienMaara() == 1);
    }

    @Test
    public void paataMitaVarataanTest() {
        Vuoro v = AI.paataMitaVarataan(keho, poyta);
        assertTrue(v.toiminto.equals(VuoronToiminto.VARAA));
        assertTrue("varattavan omistuksen pitäisi löytyä pöydältä!",
                poyta.getNakyvienNimet().contains(v.varattavanOmistuksenNimi));
    }

    @Test
    public void AIIntegraatioTest() {
        //ensisijaisesti nostetaan karkkeja
        Vuoro v = AI.suunnitteleVuoro(keho, poyta);
        assertTrue(v.toiminto == VuoronToiminto.NOSTA);

        keho.setKarkit(new int[]{0,2,3,2,1,1});
        //jos karkkeja on paljon, varataan omaisuutta
        v = AI.suunnitteleVuoro(keho, poyta);
        assertTrue(v.toiminto == VuoronToiminto.VARAA);
        assertTrue("annettu nimi on validi",poyta.teeVaraus(keho, v.varattavanOmistuksenNimi));
        assertTrue("varataan ensisijaisesti sellainen omistus, johon olisi varaa", keho.onkoVaraa(keho.getVaraukset().get(0).getOmistus()));

        //karkkeja ei mahdu enää hamstraamaan, joten ostetaan jotain!
        v = AI.suunnitteleVuoro(keho, poyta);
        assertTrue(v.toiminto == VuoronToiminto.OSTA);
        assertTrue("ensisijaisesti ostetaan varaus", poyta.suoritaOstoVarauksista(keho, Integer.parseInt(v.ostettavanOmaisuudenNimi)));
        assertTrue("varaus on lunastettu", keho.getVaraukset().size() == 0);

        keho.setKarkit(new int[]{5,1,1,1,1,1});
        //ei ole enää varauksia ja karkit on täynnä -> ostetaan pöydästä
        v = AI.suunnitteleVuoro(keho, poyta);
        assertTrue(v.toiminto == VuoronToiminto.OSTA);
        assertTrue("nimi oli validi", poyta.suoritaOsto(keho, Integer.parseInt(v.ostettavanOmaisuudenNimi)));
    }
}
