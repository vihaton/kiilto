package main.ui.gui.toiminnankuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import main.Pelivelho;

/**
 * Kuuntelee nappeja, joilla pelaaja päättää mitä tekee vuorollaan.
 *
 * @author xvixvi
 */
public class ToimintonapinKuuntelija implements ActionListener {

    private final Pelivelho pelivelho;
    private final JComponent[] napit;
    private final String teksti;

    /**
     * Luo kuuntelijan.
     * 
     * @param pelivelho Veikkaapa.
     * @param n tämän napin teskti.
     * @param napit napit, joiden näkyvyyttä säädetään.
     */
    public ToimintonapinKuuntelija(Pelivelho pelivelho, JButton n, JComponent[] napit) {
        this.pelivelho = pelivelho;
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
        } else if (teksti.contains("Ostan")) {
            napit[2].setVisible(true);
            napit[3].setVisible(true);
            napit[0].setVisible(false);
            pelivelho.vieVarauksetJaNakyvatOmistuksetValitsimelle();
        } else if (teksti.contains("Varaan")) {
            napit[2].setVisible(true);
            napit[3].setVisible(true);
            napit[0].setVisible(false);
            pelivelho.vieNakyvatOmistuksetValitsimelle();
        }
    }
}
