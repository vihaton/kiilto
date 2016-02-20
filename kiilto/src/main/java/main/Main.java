package main;

import ui.gui.AlkuIkkuna;
import javax.swing.SwingUtilities;

/**
 * Pistetään peli pyörimään!
 * 
 * @author xvixvi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AlkuIkkuna k = new AlkuIkkuna();
        SwingUtilities.invokeLater(k);
    }

}
