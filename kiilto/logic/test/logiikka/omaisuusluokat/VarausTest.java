/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.omaisuusluokat;

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
public class VarausTest {
    
    public VarausTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        v = new Varaus(new Omistus("test", 1, 5, new int[]{0,1,1,1,0,0}));
    }
    
    @After
    public void tearDown() {
    }

    Varaus v;
    
    @Test
    public void getHintakasatToimii() {
        assertTrue(v.getHintaKasat().getKarkkienMaara() == 3);
    }
    
    @Test
    public void toStringToimiiJotenkin() {
        assertTrue(v.toString().contains(v.getOmistus().getNimi()));
    }
}
