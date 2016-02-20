
package ui.toiminnankuuntelijat;

import java.awt.event.*;
import javax.swing.*;
import logiikka.Pelivelho;

/**
 *
 * @author xvixvi
 */
public class Tekstinsyotonkuuntelija implements ActionListener{
    
    private Pelivelho pelivelho;
    private JTextField tekstikentta;

    public Tekstinsyotonkuuntelija(Pelivelho pv, JTextField jtf) {
        pelivelho = pv;
        tekstikentta = jtf;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String syote = tekstikentta.getText();
        if (syote == null) {
            syote = "";
        }
        pelivelho.jatketaan(syote);
    }

}
