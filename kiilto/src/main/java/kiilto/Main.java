package kiilto;

import java.util.Scanner;
import ui.Pelivelho;
import logiikka.*;
/**
 *
 * @author xvixvi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pelivelho joku = new Pelivelho(new Scanner(System.in));
        joku.alustaPeli();
    }
    
}
