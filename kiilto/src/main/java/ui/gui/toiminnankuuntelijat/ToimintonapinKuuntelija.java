package ui.gui.toiminnankuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author xvixvi
 */
public class ToimintonapinKuuntelija implements ActionListener {

    private final JButton nappi;
    private final JComponent[] napit;
    private final String teksti;

    public ToimintonapinKuuntelija(JButton n, JComponent[] napit) {
        nappi = n;
        this.napit = napit;
        teksti = n.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (teksti.contains("Nostan")) {
            napit[1].setVisible(true);
            napit[3].setVisible(true);
            napit[0].setVisible(false);
        } else if (teksti.contains("takaisin")) {
            napit[0].setVisible(true);
            for (int i = 1; i < 4; i++) {
                napit[i].setVisible(false);
            }
        } else {
            napit[2].setVisible(true);
            napit[3].setVisible(true);
            napit[0].setVisible(false);
        }


    }

}
