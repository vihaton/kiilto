package logiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xvixvi
 */
public class PelivelhoTest {

    @Before
    public void setUp() {
        p = new Pelivelho();
        p.luoPelaajat(3);
        p.setPoyta(new Poyta(p.getPelaajat()));
    }

    private Pelivelho p;
    /*
     Pelivelhon testaaminen on osittain vajavaista, koska osaan metodeista liittyy oleellisesti
     käyttöliittymä, jota testataan (ainakin näillä taidoilla) ainostaan käsin.
     */

    @Test
    public void getteritToimii() {
        assertTrue(p.getKierros().equals("1"));
        assertTrue(p.getPelaajat().size() == 3);
        assertTrue(p.getPoyta() != null);
        assertTrue(p.getVuorossaOleva().equals("Pelaaja1"));
    }

    @Test
    public void kierroksenViimeinen() {
        assertFalse(p.kierroksenViimeinen());
        p.seuraavaPelaajanVuoro();
        assertFalse(p.kierroksenViimeinen());
        p.seuraavaPelaajanVuoro();
        assertTrue(p.kierroksenViimeinen());
    }

    @Test
    public void nostorajoituksenTestaus() {
        assertTrue(p.vahintaanNeljaKarkkia(1));
        p.getPoyta().getMarkkinat().kasvataKasaa(1, -4);
        assertFalse(p.vahintaanNeljaKarkkia(1));
        assertTrue(p.vahintaanNeljaKarkkia(0));
    }

    @Test
    public void nostaNallekarkkejaToimiiOikeallaSyotteella() {
        p.nostaNallekarkkeja(new int[]{0, 1, 1, 1, 0});
        assertTrue(p.getPoyta().getMarkkinat().getKarkkienMaara() == 27);
        assertTrue(p.getPelaajat().get(0).getKarkit().getKarkkienMaara() == 3);
        p.seuraavaPelaajanVuoro();
        p.nostaNallekarkkeja(new int[]{2, 0, 0, 0, 0});
        assertTrue(p.getPoyta().getMarkkinat().getKarkkienMaara() == 25);
        assertTrue(p.getPelaajat().get(1).getKarkit().getKarkkienMaara() == 2);
    }

    @Test
    public void ostaminenToimii() {
        assertFalse(p.osta(p.getPoyta().getNakyvienNimet().get(0)));
        assertTrue(p.getPelaajat().get(0).getOmaisuudenKoko() == 0);
        p.getPelaajat().get(0).setKarkit(new int[]{5, 5, 5, 5, 5, 5});
        assertTrue(p.osta(p.getPoyta().getNakyvienNimet().get(0)));
        assertTrue(p.getPelaajat().get(0).getOmaisuudenKoko() == 1);

        //ostaminen varauksista
        p.seuraavaPelaajanVuoro();
        p.getPelaajat().get(1).setKarkit(new int[]{4, 4, 4, 4, 4, 4});
        String varattava = p.getPoyta().getNakyvienNimet().get(0);
        p.varaa(varattava);
        assertTrue(p.osta(varattava));
        assertTrue(p.getPelaajat().get(1).getOmaisuudenKoko() == 1);
    }

    @Test
    public void varaaminenToimii() {
        assertTrue(p.varaa(p.getPoyta().getNakyvienNimet().get(0)));
        assertTrue(p.varaa(p.getPoyta().getNakyvienNimet().get(0)));
        assertFalse(p.varaa("91"));
        assertTrue(p.varaa(p.getPoyta().getNakyvienNimet().get(10)));
        assertFalse(p.varaa(p.getPoyta().getNakyvienNimet().get(0)));
    }

    @Test
    public void seuraavanPelaajanVuoroToimii() {
        assertTrue(p.getKierros().equals("1"));
        for (int i = 0; i < 4; i++) {
            p.seuraavaPelaajanVuoro();
        }
        assertTrue(p.getKierros().equals("2"));
        assertTrue(p.getVuorossaOleva().equals(p.getPelaajat().get(1).getNimi()));
    }

}
