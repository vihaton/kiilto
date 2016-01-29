/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka;

import java.util.ArrayList;
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
public class PoytaTest {
    
    Poyta p;
    
    public PoytaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ArrayList<String> pelaajat = new ArrayList<>();
        pelaajat.add("homo1"); pelaajat.add("homo2");
        pelaajat.add("homo3");
        pelaajat.add("mr Gandalf");
        Pelivelho pv = new Pelivelho();
        pv.luoPelaajat(pelaajat);
        p = new Poyta(pv.getPelaajat());
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void poytaLuoLukijan() {
        assertTrue(!p.luoLukija().equals(null));
    }
    
    @Test
    public void luoOmistuksenOikein() {
        String esimerkkiRivi = "69,1,punainen,0,0,6,9,0";
        Omistus oikea = new Omistus("69", 1, "punainen", new Kasakokoelma(esimerkkiRivi.split(",",9)));
        assertTrue("ei toimi oikealla syötteellä\n" + oikea.toString() +"\n" +p.luoOmistus(esimerkkiRivi).toString(),
                oikea.toString().equals(p.luoOmistus(esimerkkiRivi).toString()));
    }
    
    @Test
    public void luoOikeanMaaranOmistuksiaAlustuksessa() {
        assertTrue("omistuspakkoja ei ole oikeaa määrää", p.getOmistuspakat().size() == 3);
        Omaisuus pakka1 = p.getOmistuspakat().get(0);
        Omaisuus pakka2 = p.getOmistuspakat().get(1);
        Omaisuus pakka3 = p.getOmistuspakat().get(2);
        assertTrue("omistuspakassa 1 väärä määrä omistuksia", pakka1.getOmaisuudenKoko() == 40);
        assertTrue("omistuspakassa 2 väärä määrä omistuksia", pakka2.getOmaisuudenKoko() == 30);
        assertTrue("omistuspakassa 3 väärä määrä omistuksia", pakka3.getOmaisuudenKoko() == 20);
    }
}
