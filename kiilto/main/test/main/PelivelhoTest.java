package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author xvixvi
 */
public class PelivelhoTest {

    private Pelivelho p;

    @Before
    public void setup() {
        p = new Pelivelho();
        p.alustaPeli(new boolean[3]);
    }

    @Test
    public void nostorajoituksenTestaus() {
        assertTrue(p.vahintaanNeljaKarkkia(1));
        p.getPoyta().getMarkkinat().kasvataKasaa(1, -4);
        assertFalse(p.vahintaanNeljaKarkkia(1));
        assertTrue(p.vahintaanNeljaKarkkia(0));
    }
}
