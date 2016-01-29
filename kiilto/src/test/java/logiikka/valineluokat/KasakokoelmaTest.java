
package logiikka.valineluokat;

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
public class KasakokoelmaTest {
    
    Kasakokoelma k;
    
    public KasakokoelmaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        k = new Kasakokoelma(0);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void konstruktoriToimiiPelaajalle() {
        for (int i = 0; i < 6; i++) {
            assertTrue("toimii väärin oikealla syötteellä", k.getKasanKoko(i) == 0);
        }
    }
    
    @Test
    public void konstruktoriToimii2PelaajanPöydälle() {
        k = new Kasakokoelma(2);
        for (int i = 1; i < 6; i++) {
            assertTrue(k.getKasanKoko(i) == 4);
        }
        assertTrue("väärä määrä kultaa", k.getKasanKoko(0)==5);
    }
    
    @Test
    public void konstruktoriToimii3PelaajanPöydälle() {
        k = new Kasakokoelma(3);
        for (int i = 1; i < 6; i++) {
            assertTrue(k.getKasanKoko(i) == 5);
        }
        assertTrue("väärä määrä kultaa", k.getKasanKoko(0)==5);
    }
    
    @Test
    public void konstruktoriToimii4PelaajanPöydälle() {
        k = new Kasakokoelma(4);
        for (int i = 1; i < 6; i++) {
            assertTrue(k.getKasanKoko(i) == 7);
        }
        assertTrue("väärä määrä kultaa", k.getKasanKoko(0)==5);
    }
    
    @Test
    public void getKasanKokoToimii() {
        k = new Kasakokoelma(2);
        assertTrue(k.getKasanKoko(-5) == 0);
        assertTrue(k.getKasanKoko(1) == 4);
        assertTrue(k.getKasanKoko(0) == 5);
        assertTrue(k.getKasanKoko(100) == 0);
    }
}
