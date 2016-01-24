package kiilto;

import java.util.Scanner;
import ui.TUI;
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
        TUI joku = new TUI(new Scanner(System.in));
        joku.alustaPeli();
    }
    
}
