package tiralabra.tietorakenteet.apurakenteet;

import org.junit.Before;
import org.junit.Test;
import tiralabra.tietorakenteet.Kierros;
import tiralabra.tietorakenteet.Vuoro;
import tiralabra.tietorakenteet.VuoronToiminto;

import static org.junit.Assert.*;

/**
 * Created by vili on 25.8.2017.
 */
public class ListaTest {

    Lista l;

    @Before
    public void setup() {
        l = new Lista();
    }

    @Test
    public void initTest() {
        assertTrue(l != null);
        assertTrue("aina pitäisi olla tilaa tyhjään listaan", l.onkoTilaa(0));
        assertTrue("aluksi pitäisi olla tilaa ainakin 3 kokoiseen listaan",
                l.onkoTilaa(3));
        assertTrue(l.onTyhja());
        assertFalse(l.onkoIndeksiListalla(0));
        assertFalse(l.onkoIndeksiListalla(-1));
        assertFalse(l.onkoIndeksiListalla(1));
        assertFalse(l.onkoIndeksiListalla(100));
    }

    @Test
    public void lisaaJaOtaObjektiTest() {
        assertFalse(l.onkoIndeksiListalla(0));
        l.lisaa(1);
        assertFalse(l.onTyhja());
        assertTrue(l.onkoIndeksiListalla(0));
        assertFalse(l.onkoIndeksiListalla(1));
        Object asiaA = l.haeEnsimmainen();
        Object asiaB = l.haeViimeinen();
        assertTrue(asiaA != null);
        assertTrue(asiaB != null);
        assertTrue(asiaA.equals(asiaB));
        l.lisaa(2);
        assertFalse(l.onTyhja());
        asiaA = l.haeEnsimmainen();
        asiaB = l.haeViimeinen();
        assertTrue(asiaA != null);
        assertTrue(asiaB != null);
        assertFalse(asiaA.equals(asiaB));
        assertTrue("asiaA class was not Integer\n" + asiaA.getClass(), asiaA.getClass().equals(Integer.class));
    }

    @Test
    public void lisaaJaOtaVuoroTest() {
        assertFalse(l.onkoIndeksiListalla(0));
        l.lisaa(new Vuoro(VuoronToiminto.ENTEEMITAAN));
        assertFalse(l.onTyhja());
        assertTrue(l.onkoIndeksiListalla(0));
        assertFalse(l.onkoIndeksiListalla(1));
        Object asiaA = l.haeEnsimmainen();
        Object asiaB = l.haeViimeinen();
        assertTrue(asiaA != null);
        assertTrue(asiaB != null);
        assertTrue(asiaA.equals(asiaB));
        l.lisaa(new Vuoro(VuoronToiminto.OSTA_POYDASTA));
        assertFalse(l.onTyhja());
        asiaA = l.haeEnsimmainen();
        asiaB = l.haeViimeinen();
        assertTrue(asiaA != null);
        assertTrue(asiaB != null);
        assertFalse(asiaA.equals(asiaB));
        assertTrue("asiaA class was not Vuoro\n" + asiaA.getClass(), asiaA.getClass().equals(Vuoro.class));
    }

    @Test
    public void OtaIndeksistaTest() {
        for (int i = 0; i < 5; i++) {
            l.lisaa(new Kierros());
        }
        assertFalse(l.onTyhja());
        Object asia = l.haeIndeksista(-1);
        assertTrue(asia == null);
        asia = l.haeIndeksista(5);
        assertTrue(asia == null);
        asia = l.haeIndeksista(3);
        assertTrue(asia != null);
        Object asiaB = l.haeIndeksista(3);
        assertTrue(asia.equals(asiaB));
        asiaB = l.haeIndeksista(1);
        assertFalse(asia.equals(asiaB));
    }

    @Test
    public void onkoObjektiaTest() {
        for (int i = 0; i < 5; i++) {
            l.lisaa("string " + i);
        }
        assertFalse(l.onkoIndeksiListalla(-1));
        assertFalse(l.onkoIndeksiListalla(5));
        assertTrue(l.onkoIndeksiListalla(4));
        assertTrue(l.onkoIndeksiListalla(0));
    }

    @Test
    public void lisaaPaljonOtuksiaTest() {
        for (int i = 0; i < 100; i++) {
            l.lisaa(i);
        }
        assertFalse(l.onTyhja());
        assertTrue(l.onkoIndeksiListalla(99));
        assertFalse(l.onkoIndeksiListalla(100));
        assertTrue(l.haeIndeksista(69) != null);
    }

    @Test
    public void tuplaaPituusToimii() {
        assertTrue("alkupituus",l.getElementinKoko() == 10);
        for (int i = 0; i < 10; i++) {
            l.lisaa(i);
        }
        assertTrue("alkupituus",l.getElementinKoko() == 10);
        l.lisaa("otus");
        assertTrue(l.getElementinKoko() == 20);
        l.tuplaaPituus();
        assertTrue(l.getElementinKoko() == 40);
    }

    @Test
    public void kokoTest() {
        assertTrue("alussa koko on 0", l.koko() == 0);
        for (int i = 1; i < 100; i++) {
            l.lisaa(i);
            assertTrue("koko on i", l.koko() == i);
        }
    }
}
