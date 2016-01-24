
package logiikka;

import ui.TUI;
import java.util.Scanner;
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
public class PelivelhoTest {
    TUI pv;
    
    public PelivelhoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        pv = new TUI(new Scanner(System.in));
    }
    
    @After
    public void tearDown() {
    }


}
