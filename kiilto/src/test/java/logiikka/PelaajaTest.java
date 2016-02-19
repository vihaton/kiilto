
package logiikka;

import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;
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
        
        p.setKarkit(new int[]{1,1,1,1,1,1});
        
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
        Omaisuus oma1 = new Omaisuus();
        Omistus o1 = new Omistus("test", 1, 5, new int[]{0,1,1,1,0,0});
        oma1.lisaaOmistus(o1);
        Kasakokoelma k = new Kasakokoelma(0);
        
        p.setKarkit(new int[]{1,2,2,1,0,0});
        
        p.osta(oma1, o1, k);
        
        assertTrue(p.voittaja(1)); //omistus meni perille
        assertTrue(p.getKarkit().getKarkkienMaara() == 3); //karkkien määrä väheni
        assertTrue(k.getKarkkienMaara()==3); //karkit menivät perille
        assertTrue(oma1.onkoPA()); //omistus poistui alk.per.omaisuudesta
        
        Omistus o2 = new Omistus("test", 2, 1, new int[]{0,1,1,1,0,0});
        oma1.lisaaOmistus(o2);
        
        p.osta(oma1, o2, k); //nyt ostamiseen tarvitaan kultaa
        
        assertTrue(p.voittaja(3)); //omistus meni perille ja kasvatti arvovaltaa
        assertTrue("Oston jälkeen pelaajalla ei pitänyt olla enää karkkeja, "
                + "löytyi " + p.getKarkit().getKarkkienMaara(),
                p.getKarkit().getKarkkienMaara() == 0); //karkkien määrä väheni
        assertTrue(k.getKarkkienMaara()==6); //karkit menivät perille
        assertTrue(oma1.onkoPA()); //omistus poistui alk.per.omaisuudesta
        
    }
    
    @Test
    public void siirraKarkitToimii() {
        p.setKarkit(new int[]{0,1,1,1,1,1});
        Kasakokoelma k = new Kasakokoelma(0);
        
        //metodi on toistaiseksi private, jos muutetaan julkiseksi
        //niin katsotaan sitten uudestaan.
    }
    
    @Test
    public void liikaaKarkkejaToimii() {
        assertFalse(p.liikaaKarkkeja());
        
        p.setKarkit(new int[]{5,5,5,5,5,5});
        assertTrue(p.liikaaKarkkeja());
        
        p.setKarkit(new int[]{0,5,5,0,0,0});
        assertFalse(p.liikaaKarkkeja());
        
        p.getKarkit().kasvataKasaa(3, 1);
        assertTrue(p.liikaaKarkkeja());
        
        p.setKarkit(new int[]{0,0,0,0,0,11});
        assertTrue(p.liikaaKarkkeja());
    }
    
    @Test
    public void merkkiHenkiloVieraileeToimii() {
        p.merkkihenkiloVierailee(new Merkkihenkilo("nimi",new int[]{0,3,3,3,0,0},3));
        assertTrue(p.getMerkkihenkiloidenArvo()==3);
    }
    
    @Test
    public void getMerkkihenkiloidenArvoToimii() {
        assertTrue(p.getMerkkihenkiloidenArvo() == 0);
        p.merkkihenkiloVierailee(new Merkkihenkilo(new int[]{0,1,1,1,1,1}));
        assertTrue(p.getMerkkihenkiloidenArvo() == 3);
        p.merkkihenkiloVierailee(new Merkkihenkilo(new int[]{0,1,1,1,1,1}));
        assertTrue(p.getMerkkihenkiloidenArvo() == 6);
    }
    
    @Test
    public void getVarausToimii() {
        assertTrue(p.getVaraus(100)==null);
        Omistus o = new Omistus(""+100, 1, 1, new Kasakokoelma(0));
        p.teeVaraus(o);
        assertTrue(p.getVaraus(100) == o);
        p.teeVaraus(new Omistus(""+101, 1, 1, new Kasakokoelma(0)));
        assertTrue(p.getVaraus(100) == o);
        assertTrue(p.getVaraus(101) != o && p.getVaraus(101) != null);
    }
    
    @Test
    public void teeVarausToimii() {
        assertFalse(p.teeVaraus(null));
        for (int i = 0; i < 3; i++) {
            assertTrue(p.teeVaraus(new Omistus(""+i,1,1,new Kasakokoelma(0))));
        }
        assertFalse(p.teeVaraus(new Omistus("o",1,1,new Kasakokoelma(0))));
        assertTrue(p.getVaraus(1) != null);
    }
    
    @Test
    public void ostaVarausToimii() {
        assertFalse(p.ostaVaraus(null, null));
        assertTrue(p.getVarauksienMaara()==0);
        
        Omistus ostettava = new Omistus("o",1,1,new int[]{0,2,2,2,0,0});
        assertFalse(p.ostaVaraus(ostettava, null));
        assertTrue(p.getVarauksienMaara()==0);
        
        Kasakokoelma k = new Kasakokoelma(0);
        assertFalse(p.ostaVaraus(ostettava, k));
        assertTrue(p.getVarauksienMaara()==0);
        
        p.teeVaraus(ostettava);
        p.setKarkit(new int[]{1,2,2,1,0,0});
        
        assertTrue(p.ostaVaraus(ostettava, k));
        
        assertTrue(p.getKarkit().getKarkkienMaara()==0);
        assertTrue(k.getKarkkienMaara()==6);
        assertTrue(p.getVarauksienMaara()==0);
        assertTrue(p.voittaja(1));
    }
}
