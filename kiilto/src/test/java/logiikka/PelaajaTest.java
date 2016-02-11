
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
        p.lisaaOmistus(o);
        int[] korjattuHinta = p.getHintaOmaisuustulotHuomioituna(o);
        assertTrue("hinnassa ei ollut kaikkia kasoja", korjattuHinta.length == 6);
        
        for (int i = 2; i < 6; i++) {
            assertTrue(korjattuHinta[i] == 5);
        }
        
        int kh = korjattuHinta[1];
        assertTrue("pitäisi olla 4, oli "+kh, kh == 4);
        
        o = new Omistus("2", 1, 1 , new Kasakokoelma(3));
        p.lisaaOmistus(o);
        korjattuHinta = p.getHintaOmaisuustulotHuomioituna(o);
        
        assertTrue(korjattuHinta[1] == 3 );
    }
    
    @Test
    public void onkoVaraaToimii() {
        assertFalse(p.onkoVaraa(null));
        
        Omistus o = new Omistus("1", 1, 1, new int[]{0,2,2,2,2,2});
        assertFalse(p.onkoVaraa(o));
        
        p.setKarkit(new int[]{0,4,4,4,4,4});
        assertTrue(p.onkoVaraa(o));
        
        o = new Omistus("2", 2, 2, new int[]{0,5,0,0,0,0});
        assertFalse(p.onkoVaraa(o));
        
        p.getKarkit().kasvataKasaa(0, 1);
        assertTrue(p.onkoVaraa(o));
        
        o = new Omistus("3", 2, 2, new int[]{0,5,0,5,0,0});
        assertFalse(p.onkoVaraa(o));
        
        p.getKarkit().kasvataKasaa(0, 1);
        assertTrue(p.onkoVaraa(o));
    }
    
    @Test
    public void ostaToimii() {
        
    }
}
