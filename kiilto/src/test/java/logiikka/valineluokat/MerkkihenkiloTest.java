/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.valineluokat;

import logiikka.omaisuusluokat.Omaisuus;
import logiikka.omaisuusluokat.Omistus;
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
public class MerkkihenkiloTest {
    
    public MerkkihenkiloTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        m = new Merkkihenkilo(new int[]{0,3,3,3,0,0});
    }
    
    @After
    public void tearDown() {
    }

    Merkkihenkilo m;
    
    @Test
    public void vakuuttuukoOmaisuudestaToimii() {
        Omaisuus om = new Omaisuus();
        om.lisaaOmistus(new Omistus("1", 1, 1, new int[]{0,1,1,1,0,0}));
        
        assertFalse(m.vaikuttuukoOmaisuudesta(om));
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                om.lisaaOmistus(new Omistus(""+i+""+j, 0, i+1, new Kasakokoelma(0)));
            }
        }
        
        assertTrue(m.vaikuttuukoOmaisuudesta(om));
    }
}
