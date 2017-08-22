package logiikka;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by vili on 22.8.2017.
 */
public class PelinpystyttajaTest {

    private Pelinpystyttaja pp;

    @Before
    public void setUp() {
        pp = new Pelinpystyttaja(3);
    }


    @Test
    public void nostaNallekarkkejaToimiiOikeallaSyotteella() {
        pp.nostaNallekarkkeja(new int[]{0, 0, 1, 1, 1, 0});
        assertTrue(pp.poyta.getMarkkinat().getKarkkienMaara() == 27);
        assertTrue(pp.pelaajat.get(0).getKarkit().getKarkkienMaara() == 3);
        pp.seuraavanPelaajanVuoro();
        pp.nostaNallekarkkeja(new int[]{0, 2, 0, 0, 0, 0});
        assertTrue(pp.poyta.getMarkkinat().getKarkkienMaara() == 25);
        assertTrue(pp.pelaajat.get(1).getKarkit().getKarkkienMaara() == 2);
    }

    @Test
    public void ostaminenToimii() {
        assertFalse(pp.osta(pp.poyta.getNakyvienNimet().get(0)));
        assertTrue(pp.pelaajat.get(0).getOmaisuudenKoko() == 0);
        pp.pelaajat.get(0).setKarkit(new int[]{5, 5, 5, 5, 5, 5});
        assertTrue(pp.osta(pp.poyta.getNakyvienNimet().get(0)));
        assertTrue(pp.pelaajat.get(0).getOmaisuudenKoko() == 1);

        //ostaminen varauksista
        pp.seuraavanPelaajanVuoro();
        pp.pelaajat.get(1).setKarkit(new int[]{4, 4, 4, 4, 4, 4});
        String varattava = pp.poyta.getNakyvienNimet().get(0);
        pp.varaa(varattava);
        assertTrue(pp.osta(varattava));
        assertTrue(pp.pelaajat.get(1).getOmaisuudenKoko() == 1);
    }

    @Test
    public void varaaminenToimii() {
        assertTrue(pp.varaa(pp.poyta.getNakyvienNimet().get(0)));
        assertTrue(pp.varaa(pp.poyta.getNakyvienNimet().get(0)));
        assertFalse(pp.varaa("91"));
        assertTrue(pp.varaa(pp.poyta.getNakyvienNimet().get(10)));
        assertFalse(pp.varaa(pp.poyta.getNakyvienNimet().get(0)));
    }

    @Test
    public void seuraavanPelaajanVuoroToimii() {
        assertTrue(pp.kierros == 1);
        for (int i = 0; i < 4; i++) {
            pp.seuraavanPelaajanVuoro();
        }
        assertTrue(pp.kierros == 2);
        assertTrue(pp.vuorossaOleva.equals(pp.pelaajat.get(1)));
    }


    @Test
    public void kierroksenViimeinen() {
        assertFalse(pp.kierroksenViimeinen());
        pp.seuraavanPelaajanVuoro();
        assertFalse(pp.kierroksenViimeinen());
        pp.seuraavanPelaajanVuoro();
        assertTrue(pp.kierroksenViimeinen());
    }
}
