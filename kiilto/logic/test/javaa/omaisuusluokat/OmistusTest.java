
package javaa.omaisuusluokat;

import javaa.omaisuusluokat.Omistus;
import javaa.valineluokat.Kasakokoelma;
import javaa.valineluokat.Vari;
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
public class OmistusTest {
    
    Omistus o;
    
    public OmistusTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        o = new Omistus("testiomistus", 1, Vari.KULTAINEN, new Kasakokoelma(0));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void konstruktoritToimivatOikein() {
        Omistus o2 = new Omistus("testiomistus", 1, 0, new Kasakokoelma(0));
        Omistus o3 = new Omistus("testiomistus", 1, "kultainen", new Kasakokoelma(0));
        assertTrue("konstruktorit tuottavat erilaiset omistukset", o.toString().equals(o2.toString()) && o.toString().equals(o3.toString()));
    }
    
    @Test
    public void getKasanToimiiTyhjillaKasoilla() {
        for (int i = 0; i < 6; i++) {
            assertTrue("tyhjät kasat eivät ole tyhjiä", o.getKasanKoko(i)==0);
        }
    }
    
    @Test
    public void getKasanKokoToimiiOikeinVaarallaSyotteella() {
        assertTrue(o.getKasanKoko(-1)==0);
        assertTrue(o.getKasanKoko(100)==0);
    }
    
    @Test
    public void getLisaKarkinVariNumeronaToimii() {
        o = new Omistus("testi", 666, 0, new Kasakokoelma(0));
        assertTrue(o.getLisaKarkinVari().equals(Vari.KULTAINEN) && o.getLisaKarkinVariNumerona()==0);
        o = new Omistus("testi", 666, 1, new Kasakokoelma(0));
        assertTrue(o.getLisaKarkinVari().equals(Vari.VALKOINEN) && o.getLisaKarkinVariNumerona()==1);
        o = new Omistus("testi", 666, 2, new Kasakokoelma(0));
        assertTrue(o.getLisaKarkinVari().equals(Vari.SININEN) && o.getLisaKarkinVariNumerona()==2);
        o = new Omistus("testi", 666, 3, new Kasakokoelma(0));
        assertTrue(o.getLisaKarkinVari().equals(Vari.VIHREA) && o.getLisaKarkinVariNumerona()==3);
        o = new Omistus("testi", 666, 4, new Kasakokoelma(0));
        assertTrue(o.getLisaKarkinVari().equals(Vari.PUNAINEN) && o.getLisaKarkinVariNumerona()==4);
    }
}
