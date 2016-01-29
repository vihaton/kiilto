/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.omaisuusluokat;

import logiikka.valineluokat.Kasakokoelma;
import logiikka.valineluokat.Vari;
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
}
