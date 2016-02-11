
package logiikka;

import logiikka.omaisuusluokat.Omaisuus;
import logiikka.omaisuusluokat.Omistus;
import logiikka.valineluokat.Kasakokoelma;
import logiikka.valineluokat.Nallekarkkikasa;
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
public class PelaajaTest {
    
    public PelaajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        p = new Pelaaja("testi");
    }
    
    @After
    public void tearDown() {
    }

    private Pelaaja p;
    
    @Test
    public void setKarkitToimii() {
        assertFalse(p.setKarkit(new int[0]));
        assertTrue(p.setKarkit(new int[]{1,1,1,1,1,1}));
        Kasakokoelma k = p.getKarkit();

        for (int i = 0; i < 6; i++) {
            assertTrue(k.getKasanKoko(i)==1);
        }
    }
    
    @Test
    public void voittajaToimii() {
        p.setKarkit(new int[]{5,5,5,5,5});
        assertFalse(p.voittaja(1));
        
        Omaisuus o = new Omaisuus();
        Omistus o1 = new Omistus("", 2,2, new Kasakokoelma(0));
        o.lisaaOmistus(o1);
        p.osta(o, o1 , new Kasakokoelma(2));
        assertTrue(p.voittaja(1));
        
        o.lisaaOmistus(new Omistus("1", 5, 2, new Kasakokoelma(0)));
        p.osta(o, o.getNakyvaOmistus(1), new Kasakokoelma(0));
        assertTrue("isommalla omaisuudella ei toimi", p.voittaja(3));
    }
    
    @Test
    public void hintaOmaisuustulotHuomioitunaToimii() {
        Omistus o = new Omistus("1", 1, 1, new Kasakokoelma(3));
        int[] korjattuHinta = p.getHintaOmaisuustulotHuomioituna(o);
        assertTrue("odotettu hinta oli 5 kasan kokoinen, siis ilman kultakarkkeja",
                korjattuHinta.length == 5);
        
        for (int i = 1; i < 5; i++) {
            assertTrue(korjattuHinta[i] == 5);
        }
        
        int kh = korjattuHinta[0];
        assertTrue("pitäisi olla 4, oli "+kh, kh == 4);
        
        o = new Omistus("2", 1, 1 , new Kasakokoelma(3));
        korjattuHinta = p.getHintaOmaisuustulotHuomioituna(o);
        
        assertTrue(korjattuHinta[0] == 3 );
    }
}
