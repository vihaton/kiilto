
package ui;

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
    Pelivelho pv;
    
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
        pv = new Pelivelho(new Scanner(System.in));
    }
    
    @After
    public void tearDown() {
    }


}
