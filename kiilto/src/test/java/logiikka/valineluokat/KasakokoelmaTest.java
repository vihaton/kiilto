
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
        k = new Kasakokoelma(0); //luo tyhjän kasakokoelman
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
    
    @Test
    public void toStringIlmanKultaaTulostaaVainViisiKasaa() {
        String[] palat = k.toStringIlmanKultaa().split(":");
        assertTrue(palat.length == 6);
    }
    
    @Test
    public void kasvataKasaaToimii() {
        k.kasvataKasaa(0,5);
        assertTrue("kultakasaa kasvatettiin 5.llä, lopputuloksena kasassa " + k.getKasanKoko(0),
                k.getKasanKoko(0) == 5);
        k.kasvataKasaa(3, 10);
        assertTrue(k.getKasanKoko(3) == 10);
        k.kasvataKasaa(3, -100);
        assertTrue(k.getKasanKoko(3) <= 0);
    }
    
    @Test
    public void siirraKasastaToiseenToimii() {
        k = new Kasakokoelma(2);
        Kasakokoelma tyhjatKasat = new Kasakokoelma(0);
        int maksajallaKultaa = k.getKasanKoko(0);
        int maksValkoisia = k.getKasanKoko(1);
        
        k.siirraToiseenKasaan(tyhjatKasat, 1, maksValkoisia);
        assertTrue("laillinen siirto ei toimi", tyhjatKasat.getKasanKoko(1) == maksValkoisia);
        
        k.siirraToiseenKasaan(tyhjatKasat, 1, maksValkoisia);
        tyhjatKasat = new Kasakokoelma(0);
        assertTrue("tyhjästä on paha nyhjästä!", tyhjatKasat.getKasanKoko(1) == 0);
        
        k.siirraToiseenKasaan(tyhjatKasat, 0, 100);
        assertTrue("siirretään maksimissaan kaikki mitä löytyy", tyhjatKasat.getKasanKoko(0) == maksajallaKultaa);
    }
    
    @Test
    public void getKarkkienMaaraToimii() {
        k = new Kasakokoelma(0);
        assertTrue(k.getKarkkienMaara()==0);
        k = new Kasakokoelma(2);
        assertTrue(k.getKarkkienMaara()==25);
        k.kasvataKasaa(5, -100);
        assertTrue(k.getKarkkienMaara()==21);
    }
}
