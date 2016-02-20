package main;

import ui.gui.Kayttoliittyma;
import javax.swing.SwingUtilities;
import ui.*;

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
        Kayttoliittyma k = new Kayttoliittyma();
        SwingUtilities.invokeLater(k);
    }

}
