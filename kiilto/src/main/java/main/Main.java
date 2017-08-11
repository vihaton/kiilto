package main;

import ui.gui.Alkuikkuna;
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
        Alkuikkuna k = new Alkuikkuna();
        SwingUtilities.invokeLater(k);
        
        //TUI peli
//        new TUIPelivelho().pelaaTUI();
    }

}
