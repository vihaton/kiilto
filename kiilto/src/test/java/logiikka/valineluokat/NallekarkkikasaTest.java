
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
public class NallekarkkikasaTest {
    
    public NallekarkkikasaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    Nallekarkkikasa kasa;
    
    @Before
    public void setUp() {
        kasa = new Nallekarkkikasa(1, 5);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void konstruktoriToimii() {
        assertTrue(kasa.getKoko() == 5 && kasa.getVari().equals(Vari.VALKOINEN));
        kasa = new Nallekarkkikasa(0);
        assertTrue(kasa.getKoko() == 0 && kasa.getVari().equals(Vari.KULTAINEN));
    }
    
    @Test
    public void kasvataKasaaToimii() {
        kasa.kasvata(5);
        assertTrue(kasa.getKoko() == 10);
        kasa.kasvata(1);
        assertTrue(kasa.getKoko()==11);
        kasa.kasvata(-100);
        assertTrue(kasa.onTyhja());
    }
    
    @Test
    public void onTyhjaToimii() {
        assertTrue(!kasa.onTyhja());
        kasa = new Nallekarkkikasa(5);
        assertTrue(kasa.onTyhja());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
