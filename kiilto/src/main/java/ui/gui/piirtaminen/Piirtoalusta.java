package ui.gui.piirtaminen;

import ui.gui.piirtaminen.Piirtoavustaja;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import logiikka.Pelivelho;

/**
 * Luokka vastaa pelin elementtien piirtämisestä.
 *
 * @author xvixvi
 */
public class Piirtoalusta extends JPanel {

    Pelivelho pelivelho;
    Piirtoavustaja piirtoAvustaja;

    /**
     * Luo piirtoalusta, joka muistaa pelivelhon ja piirtoavustajan.
     * 
     * @param pv pelivelho.
     * @param pa piirtoavustaja.
     */
    public Piirtoalusta(Pelivelho pv, Piirtoavustaja pa) {
        super.setBackground(Color.lightGray);
        pelivelho = pv;
        piirtoAvustaja = pa;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        pelivelho.piirra(graphics, piirtoAvustaja);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
