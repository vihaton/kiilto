package ui.gui.toiminnankuuntelijat;

import javax.swing.*;
import logiikka.Pelivelho;
import ui.gui.Piirtoalusta;

/**
 *
 * @author xvixvi
 */
class VuoronPaattavaKuuntelija {

    private final Pelivelho pelivelho;
    private final JComponent[] nappulat;
    private final Piirtoalusta piirtoalusta;
    private final JLabel infokentta;

    public VuoronPaattavaKuuntelija(Pelivelho pelivelho, JComponent[] napit, Piirtoalusta pa, JLabel infokentta) {
        this.pelivelho = pelivelho;
        nappulat = napit;
        piirtoalusta = pa;
        this.infokentta = infokentta;
    }

    public void lopetaVuoro() {
        pelivelho.seuraavaPelaajanVuoro();
        piirtoalusta.repaint();
        nappulat[0].setVisible(true);
        for (int i = 1; i < 4; i++) {
            nappulat[i].setVisible(false);
        }
        infokentta.setText("Pelaajan " + pelivelho.getVuorossaOleva() + " vuoro kierroksella " + pelivelho.getKierros());
    }

}
